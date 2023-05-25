from retinaface import RetinaFace
from deepface import DeepFace

import matplotlib.pyplot as plt
import cv2

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


if __name__ == "__main__":
    sylvester_img_path = r"praktiskais\bildes\Sylvester_Stallone_2015.jpg"
    dwayne_img_path = r"praktiskais\bildes\Dwayne_The_Rock_Johnson_2009_portrait.jpg"
    sylvester2_img_path = r"praktiskais\bildes\7588423560_bf88d0bc79_k.jpg"

    # img1 = cv2.imread(img1_path)
    # img2 = cv2.imread(img2_path)

    # plt.imshow(img1)
    # plt.imshow(img2)
    # plt.show()
    
    # returns true with a Euclid distance of < threshold (.68)
    same_person = DeepFace.verify(sylvester_img_path, sylvester2_img_path, model_name = 'ArcFace', detector_backend = 'retinaface')
    print(f"same_person: {same_person}")
    # returns false with a Euclid distance of > threshold (.68)
    different_people = DeepFace.verify(sylvester_img_path, dwayne_img_path, model_name = 'ArcFace', detector_backend = 'retinaface')
    print(f"different_people: {different_people}")
    # plot_faces("praktiskais\\bildes\\Abstract_Wikipedia_Team_-_Group_photo,_2022-05-12.jpg")
    # plot_faces("praktiskais\\bildes\\Group_Photo_NWPApril2021.jpg")
    # plot_faces("praktiskais\\bildes\\Group_photo_of_Wikimania_Bangladesh_2022_(3).jpg")
    # plot_faces("praktiskais\\bildes\\Rumba_Kings_Band_Group_Photo_2023.jpg")
    # plt.show()