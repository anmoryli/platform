import sys
import numpy as np
from ultralytics import YOLO
from PIL import Image
import io

def predict(image_path):
    try:
        image = Image.open(image_path)
        model = YOLO("src\\main\\resources\\static\\python\\recognize\\best.pt")
        results = model(image, show=False, verbose=False)
        names_dict = results[0].names
        probs = results[0].probs.data.tolist()
        return names_dict[np.argmax(probs)]
    except FileNotFoundError as fnf_error:
        return f"File not found error: {fnf_error}"
    except Exception as e:
        return f"Error: {str(e)}"

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Usage: python script.py <image_path>")
        sys.exit(1)
    try:
        image_path = sys.argv[1]
        result = predict(image_path)
        print(result, flush=True)
    except Exception as e:
        print(f"System Error: {str(e)}", flush=True)