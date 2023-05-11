from retinaface import RetinaFace
import cv2
import matplotlib.pyplot as plt
import math

def normalise_landmarks(facial_area: dict, landmarks: dict):
    # facial_area -> [top_left_x, top_left_y, bottom_right_x, bottom_right_y]
    # land_marks[landmark] -> [x, y]

    top_left_x = facial_area[0]
    top_left_y = facial_area[1]
    for landmark in landmarks:
        landmarks[landmark] = [landmarks[landmark][0]-top_left_x, landmarks[landmark][1]-top_left_y]
    
    return landmarks

def plot_landmarks(axs, landmarks: dict):
    for key in landmarks:
        axs.plot(landmarks[key][0], landmarks[key][1], 'ro')

def calculate_proportions(landmarks: dict):
    right_eye_x = landmarks['right_eye'][0]
    right_eye_y = landmarks['right_eye'][1]
    left_eye_x = landmarks['left_eye'][0]
    left_eye_y = landmarks['left_eye'][1]
    mouth_right_x = landmarks['mouth_right'][0]
    mouth_right_y = landmarks['mouth_right'][1]
    mouth_left_x = landmarks['mouth_left'][0]
    mouth_left_y = landmarks['mouth_left'][1]
    # distance between right_eye and left_eye
    # divided by
    # distance between mouth_righ and mouth_left
    distance_between_eyes = math.sqrt((right_eye_x-left_eye_x)**2+(right_eye_y-left_eye_y)**2)
    mouth_size = math.sqrt((mouth_right_x-mouth_left_x)**2+(mouth_right_y-mouth_left_y)**2)

    return distance_between_eyes/mouth_size

def crop_image(img_path:str, facial_area:list):
    # facial_area -> [top_left_x, top_left_y, bottom_right_x, bottom_right_y]
    # cropped like an array -> [top_left_y:bottom_left_y, top_left_x:bottom_right_x]
    top_left_x = facial_area[0]
    top_left_y = facial_area[1]
    bottom_right_x = facial_area[2]
    bottom_right_y = facial_area[3]

    image = cv2.imread(img_path)
    return image[top_left_y:bottom_right_y, top_left_x:bottom_right_x, ::-1]
    
def plot_faces(img_path: str):
    obj = RetinaFace.detect_faces(img_path)
    faces = []
    for face_key in obj:
        image = crop_image(img_path, obj[face_key]['facial_area'])
        faces.append(image) 
    fig, axs = plt.subplots(nrows=1, ncols=len(faces), figsize=(16, 9))
    i = 0
    for face_img, face_key in zip(faces, obj):
        axs[i].imshow(face_img)

        # TODO: landmarks look creepy
        # normalised_landmarks = normalise_landmarks(obj[face_key]['facial_area'], obj[face_key]['landmarks'])
        # plot_landmarks(axs[i], normalised_landmarks)

        # axs[i].set_title(calculate_proportions(normalised_landmarks))
        i = i + 1

# TODO: get bildes from "praktiskais\bildes"
# picture_paths = 
# for path in picture_paths:
#     plot_faces(path)
plot_faces("praktiskais\\bildes\\Abstract_Wikipedia_Team_-_Group_photo,_2022-05-12.jpg")
plot_faces("praktiskais\\bildes\\Group_Photo_NWPApril2021.jpg")
plot_faces("praktiskais\\bildes\\Group_photo_of_Wikimania_Bangladesh_2022_(3).jpg")
plot_faces("praktiskais\\bildes\\Rumba_Kings_Band_Group_Photo_2023.jpg")
plt.show()