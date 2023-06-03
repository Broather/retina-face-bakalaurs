
from deepface import DeepFace

import matplotlib.pyplot as plt


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

    # plot_faces("\bildes\\Abstract_Wikipedia_Team_-_Group_photo,_2022-05-12.jpg")
    # plot_faces("\bildes\\Group_Photo_NWPApril2021.jpg")
    # plot_faces("\bildes\\Group_photo_of_Wikimania_Bangladesh_2022_(3).jpg")
    # plot_faces("\bildes\\Rumba_Kings_Band_Group_Photo_2023.jpg")
    # plt.show()

# pip install retinaface
from retinaface import RetinaFace
import cv2

def add_padding(coordinates:list, i:int)->list:
    # [top_left_x, top_left_y, bottom_right_x, bottom_right_y]
    coordinates[0] -= i
    coordinates[1] -= i
    coordinates[2] += i
    coordinates[3] += i

    return coordinates

if __name__ == "__main__":
    group_photo_path = r"./20220801_Group_photo_of_women_at_Chiayi_Station.jpg"
    group_photo = cv2.imread(group_photo_path)
    faces = RetinaFace.detect_faces(group_photo_path)

for key in faces.keys():
    coordinates = faces[key]["facial_area"]
    coordinates = add_padding(coordinates, 10)
    # facial_area: [top_left_x, top_left_y, bottom_right_x, bottom_right_y]
    cropped_image = group_photo[coordinates[1]:coordinates[3], coordinates[0]:coordinates[2]]
    # saglabāt katru seju atsevišķi kā "./face-1.jpg", "./face-2.jpg" utt.
    cv2.imwrite(f"./faces/{key}.jpg", cropped_image)