document.getElementById('sendBtn').addEventListener('click', async function () {
    const userInput = document.getElementById('userInput').value.trim();
    if (userInput === '') return;

    // 显示用户消息
    appendMessage(userInput, 'user-message');

    // 发送请求到代理服务器
    const apiUrl = 'http://127.0.0.1:3000/proxy';  // 使用本地代理服务器

    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer CTQUYSHCIGdRVonLfhjF:pFWDzXNDyCVEzsxFqcHG'  // 替换为你的访问令牌
            },
            body: JSON.stringify({
                max_tokens: 8000,
                top_k: 4,
                temperature: 0.5,
                messages: [
                    {
                        role: "system",
                        content: "你是一个藏药植物药方面的专家，你只能回答该方面的问题，其他任何问题你都不能回答"
                    },
                    {
                        role: "user",
                        content: userInput
                    }
                ],
                model: "4.0Ultra",
                stream: true  // 流式响应
            })
        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error(`HTTP error! Status: ${response.status}, Error: ${errorText}`);
            appendMessage('抱歉，服务器出现错误，请稍后再试。', 'bot-message');
            return;
        }

        // 处理流式响应
        const reader = response.body.getReader();
        const decoder = new TextDecoder('utf-8');
        let buffer = '';

        while (true) {
            const { done, value } = await reader.read();
            if (done) break;

            // 解码数据并追加到缓冲区
            buffer += decoder.decode(value, { stream: true });

            // 按行分割数据
            const lines = buffer.split('\n');
            for (let i = 0; i < lines.length - 1; i++) {
                let line = lines[i].trim();

                // 去除所有 'data: ' 前缀
                while (line.startsWith('data:')) {
                    line = line.substring(5).trim();  // 去除 'data: ' 前缀
                }

                // 打印原始数据以便调试
                console.log('Processed line:', line);

                if (line === '[DONE]') {
                    // 忽略 [DONE] 标记
                    console.log('Stream done.');
                    continue;
                }

                try {
                    // 尝试解析 JSON
                    const data = JSON.parse(line);
                    console.log('Parsed data:', data);

                    // 提取并显示消息内容
                    const deltaContent = data.choices && data.choices[0] && data.choices[0].delta.content;
                    if (deltaContent) {
                        appendMessage(formatTextByWidth(deltaContent, 300), 'bot-message');  // 300是最大宽度
                    }
                } catch (parseError) {
                    console.error('Failed to parse JSON:', parseError);
                    console.error('Invalid JSON string:', line);
                }
            }

            // 保留最后一个未完成的行
            buffer = lines[lines.length - 1];
        }

        // 处理最后一个未完成的行
        if (buffer.trim()) {
            let line = buffer.trim();
            while (line.startsWith('data:')) {
                line = line.substring(5).trim();  // 去除 'data: ' 前缀
            }

            if (line === '[DONE]') {
                console.log('Stream done.');
            } else {
                try {
                    const data = JSON.parse(line);
                    const deltaContent = data.choices && data.choices[0] && data.choices[0].delta.content;
                    if (deltaContent) {
                        appendMessage(formatTextByWidth(deltaContent, 300), 'bot-message');  // 300是最大宽度
                    }
                } catch (parseError) {
                    console.error('Failed to parse final JSON:', parseError);
                    console.error('Invalid final JSON string:', line);
                }
            }
        }

    } catch (error) {
        console.error('Error:', error);
        appendMessage('抱歉，服务器出现错误，请稍后再试。', 'bot-message');
    }

    // 清空输入框
    document.getElementById('userInput').value = '';
});

function appendMessage(content, className) {
    const chatContainer = document.getElementById('chatContainer');
    const messageElement = document.createElement('div');
    messageElement.className = `message ${className}`;
    messageElement.innerHTML = content;  // 使用innerHTML以支持换行标签
    chatContainer.appendChild(messageElement);

    // 滚动到底部
    chatContainer.scrollTop = chatContainer.scrollHeight;
}

function formatTextByWidth(text, maxWidth) {
    const canvas = document.createElement('canvas');
    const context = canvas.getContext('2d');
    context.font = '16px Arial';  // 设置字体样式和大小

    let formattedText = '';
    let currentLine = '';

    text.split(' ').forEach(word => {
        const testLine = currentLine + word + ' ';
        const metrics = context.measureText(testLine);
        const testWidth = metrics.width;

        if (testWidth > maxWidth && currentLine !== '') {
            formattedText += currentLine.trim() + '<br>';
            currentLine = word + ' ';
        } else {
            currentLine = testLine;
        }
    });

    formattedText += currentLine.trim();
    return formattedText;
}