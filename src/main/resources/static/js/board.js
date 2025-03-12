// 初始化图表
function initCharts() {
    // 示例数据
    const plantData = [
        { name: '丹参', category: '根茎类', efficacyScore: 120, distribution: '四川、云南' },
        { name: '黄芪', category: '根茎类', efficacyScore: 132, distribution: '内蒙古、甘肃' },
        { name: '当归', category: '根茎类', efficacyScore: 101, distribution: '陕西、青海' },
        { name: '甘草', category: '根茎类', efficacyScore: 134, distribution: '新疆、内蒙古' },
        { name: '人参', category: '根茎类', efficacyScore: 90, distribution: '吉林、辽宁' },
        { name: '红景天', category: '全草类', efficacyScore: 85, distribution: '西藏、青海' },
        { name: '冬虫夏草', category: '菌类', efficacyScore: 150, distribution: '青海、四川' },
        { name: '雪莲', category: '全草类', efficacyScore: 110, distribution: '西藏、青海' },
        { name: '党参', category: '根茎类', efficacyScore: 115, distribution: '山西、陕西' },
        { name: '川贝母', category: '鳞茎类', efficacyScore: 105, distribution: '四川、云南' }
    ];

    // 图表1：柱状图 - 药效评分
    const chart1 = echarts.init(document.getElementById('chart1'));
    chart1.setOption({
        title: { text: '藏药植物药效评分', textStyle: { color: '#fff' } },
        xAxis: { type: 'category', data: plantData.map(item => item.name), axisLabel: { color: '#fff' } },
        yAxis: { type: 'value', axisLabel: { color: '#fff' } },
        series: [{ data: plantData.map(item => item.efficacyScore), type: 'bar' }],
        backgroundColor: 'transparent'
    });

    // 图表2：折线图 - 分布地区数量（假设每个地区为一个单位）
    const distributionCounts = {};
    plantData.forEach(item => {
        item.distribution.split('、').forEach(region => {
            if (!distributionCounts[region]) distributionCounts[region] = 0;
            distributionCounts[region]++;
        });
    });
    const regions = Object.keys(distributionCounts);
    const counts = regions.map(region => distributionCounts[region]);

    const chart2 = echarts.init(document.getElementById('chart2'));
    chart2.setOption({
        title: { text: '藏药植物分布地区数量', textStyle: { color: '#fff' } },
        xAxis: { type: 'category', data: regions, axisLabel: { color: '#fff' } },
        yAxis: { type: 'value', axisLabel: { color: '#fff' } },
        series: [{ data: counts, type: 'line' }],
        backgroundColor: 'transparent'
    });

    // 图表3：饼图 - 藏药植物种类分布
    const categories = {};
    plantData.forEach(item => {
        if (!categories[item.category]) categories[item.category] = 0;
        categories[item.category]++;
    });
    const categoryNames = Object.keys(categories);
    const categoryValues = categoryNames.map(category => categories[category]);

    const chart3 = echarts.init(document.getElementById('chart3'));
    chart3.setOption({
        title: { text: '藏药植物种类分布', textStyle: { color: '#fff' } },
        series: [{
            type: 'pie',
            data: categoryNames.map((name, index) => ({ value: categoryValues[index], name }))
        }],
        backgroundColor: 'transparent'
    });

    // 图表4：雷达图 - 药效评分与分布地区数量的综合评估（假设每个地区的药效评分平均值）
    const radarData = [];
    plantData.forEach(item => {
        const regions = item.distribution.split('、');
        regions.forEach(() => {
            radarData.push({ value: item.efficacyScore / regions.length, name: item.name });
        });
    });

    const uniqueNames = [...new Set(plantData.map(item => item.name))];
    const maxEfficacyScore = Math.max(...plantData.map(data => data.efficacyScore));

    const chart4 = echarts.init(document.getElementById('chart4'));
    chart4.setOption({
        title: { text: '藏药植物药效与分布综合评估', textStyle: { color: '#fff' } },
        radar: {
            indicator: uniqueNames.map(name => ({ name, max: maxEfficacyScore }))
        },
        series: [{
            type: 'radar',
            data: [{ value: uniqueNames.map(name => {
                const avgScore = radarData.filter(item => item.name === name).reduce((sum, item) => sum + item.value, 0) /
                    radarData.filter(item => item.name === name).length;
                return avgScore;
            }), name: '综合评估' }]
        }],
        backgroundColor: 'transparent'
    });

    // 图表5：散点图 - 药效评分与分布地区数量的关系
    const scatterData = plantData.map(item => {
        return [item.distribution.split('、').length, item.efficacyScore];
    });

    const chart5 = echarts.init(document.getElementById('chart5'));
    chart5.setOption({
        title: { text: '药效评分与分布地区数量关系', textStyle: { color: '#fff' } },
        xAxis: { type: 'value', axisLabel: { color: '#fff' }, name: '分布地区数量' },
        yAxis: { type: 'value', axisLabel: { color: '#fff' }, name: '药效评分' },
        series: [{ type: 'scatter', data: scatterData }],
        backgroundColor: 'transparent'
    });

    // 图表6：仪表盘 - 平均药效评分
    const averageEfficacyScore = plantData.reduce((sum, item) => sum + item.efficacyScore, 0) / plantData.length;

    const chart6 = echarts.init(document.getElementById('chart6'));
    chart6.setOption({
        title: { text: '平均药效评分', textStyle: { color: '#fff' } },
        series: [{ type: 'gauge', data: [{ value: averageEfficacyScore, name: '评分' }], min: 0, max: 150 }],
        backgroundColor: 'transparent'
    });
}

// 页面加载完成后初始化图表
window.onload = initCharts;