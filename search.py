def normalise_landmarks(facial_area: dict, landmarks: dict):
    # facial_area -> [top_left_x, top_left_y, bottom_right_x, bottom_right_y]
    # land_marks[landmark] -> [x, y]

    top_left_x = facial_area[0]
    top_left_y = facial_area[1]
    for landmark in landmarks:
        landmarks[landmark] = [landmarks[landmark][0]-top_left_x, landmarks[landmark][1]-top_left_y]
    
    return landmarks

def add_padding(coordinates:list, i:int) -> list:
    # coordinates = [top_left_x, top_left_y, bottom_right_x, bottom_right_y]
    coordinates[0] -= i
    coordinates[1] -= i
    coordinates[2] += i
    coordinates[3] += i

    return coordinates

def crop_image(img_path:str, facial_area:list):
    # facial_area -> [top_left_x, top_left_y, bottom_right_x, bottom_right_y]
    # cropped like an array -> [top_left_y:bottom_left_y, top_left_x:bottom_right_x]
    top_left_x = facial_area[0]
    top_left_y = facial_area[1]
    bottom_right_x = facial_area[2]
    bottom_right_y = facial_area[3]

    image = imread(img_path)
    return image[top_left_y:bottom_right_y, top_left_x:bottom_right_x, ::-1]
    
# pip install retinaface
from retinaface import RetinaFace
from deepface import DeepFace
from skimage.io import imread, imsave
import sys
import os

if __name__ == "__main__":
    IMAGES_ROOT = "images/"
    TEMP_FACES_ROOT = "temp-faces/"

    print('Number of arguments:', len(sys.argv), 'arguments.')
    print('Argument List:', str(sys.argv))
    
    image_name_to_search_by = sys.argv[1]
    faces = RetinaFace.detect_faces(f"{IMAGES_ROOT}{image_name_to_search_by}")
    print(f"Number of faces in {image_name_to_search_by}: {len(faces)}")
    if (len(faces) > 1):
        sys.exit(69)

    matching_image_names = []
    image_names_to_search_through = [file_name for file_name in os.listdir(IMAGES_ROOT)]

    print(f"Need to search through {len(image_names_to_search_through)} images")
    progress_counter = 0
    for image_name_to_compare in image_names_to_search_through:
        verification_result = DeepFace.verify(f"{IMAGES_ROOT}{image_name_to_search_by}", f"{IMAGES_ROOT}{image_name_to_compare}", model_name = 'ArcFace',
                         detector_backend = 'retinaface',
                         enforce_detection = False)
        if (verification_result["verified"]):
            matching_image_names.append(image_name_to_compare)
        progress_counter += 1
        print(f"{progress_counter}/{len(image_names_to_search_through)}, {image_name_to_compare}: {verification_result['verified']}")

    result = "\n".join(matching_image_names)
    print(result)

    with open('temp-image-names.txt', 'w') as file:
        for image_name in matching_image_names:
            file.write(image_name + '\n')
    
    sys.exit(0)