import sys
import json
import numpy as np
from ultralytics import YOLO
from PIL import Image
import io

def predict(image_path):
    try:
        # 打开图片
        image = Image.open(image_path)
        # 加载 YOLO 模型
        model = YOLO("../best.pt")
        # 进行预测
        results = model(image, show=False, verbose=False)
        # 获取类别名称字典
        names_dict = results[0].names
        # 获取概率值
        probs = results[0].probs.data.tolist()
        # 找到最大概率的索引
        max_prob_index = np.argmax(probs)
        # 获取识别结果和准确率
        result = {
            "recognition_result": names_dict[max_prob_index],
            "confidence": round(probs[max_prob_index] * 100, 2)  # 转换为百分比，保留2位小数
        }
        return json.dumps(result, ensure_ascii=False)
    except FileNotFoundError as fnf_error:
        error_result = {
            "error": f"File not found: {str(fnf_error)}"
        }
        return json.dumps(error_result, ensure_ascii=False)
    except Exception as e:
        error_result = {
            "error": f"Prediction error: {str(e)}"
        }
        return json.dumps(error_result, ensure_ascii=False)

if __name__ == '__main__':
    if len(sys.argv) < 2:
        error_result = {
            "error": "Usage: python script.py <image_path>"
        }
        print(json.dumps(error_result, ensure_ascii=False), flush=True)
        sys.exit(1)
    try:
        image_path = sys.argv[1]
        result = predict(image_path)
        print(result, flush=True)
    except Exception as e:
        error_result = {
            "error": f"System error: {str(e)}"
        }
        print(json.dumps(error_result, ensure_ascii=False), flush=True)