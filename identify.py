# pip install deepface

picture_to_find = "558.jpg"
database = "bildes"

# print(DeepFace.find(img_path=picture_to_find, db_path=database, distance_metric="euclidean_l2"))
# sylvester_img_path = r"bildes\Sylvester_Stallone_2015.jpg"
# dwayne_img_path = r"bildes\Dwayne_The_Rock_Johnson_2009_portrait.jpg"
# sylvester2_img_path = r"bildes\7588423560_bf88d0bc79_k.jpg"

# # returns true with a Euclid distance of < threshold (.68)
#     same_person = DeepFace.verify(sylvester_img_path, sylvester2_img_path, model_name = 'ArcFace', detector_backend = 'retinaface')
#     print(f"same_person: {same_person}")
#     # returns false with a Euclid distance of > threshold (.68)
#     different_people = DeepFace.verify(sylvester_img_path, dwayne_img_path, model_name = 'ArcFace', detector_backend = 'retinaface')
#     print(f"different_people: {different_people}")
from deepface import DeepFace
import pandas as pd

def first_experiement(group_photo_path, directory_to_serarch):
    dataframes = DeepFace.find(group_photo_path, r"./faces", 
                        detector_backend="retinaface",
                          distance_metric="euclidean",
                            enforce_detection=False)

    for df in dataframes:
        # atgriež indeksu ar minimālo eiklīda distanci
        min_index = df['VGG-Face_euclidean'].idxmin()
        # atgriež identitāti ar minimālo eiklīda distanci
        closest_identity = df.loc[min_index, 'identity']
        print(closest_identity)
if __name__ == "__main__":
    # group_photo_path = r"./Group_photo_of_women_at_Chiayi_Station.jpg"
    # face_directory = r"./faces"
    # first_experiement(group_photo_path, face_directory)
    face_path=r"./jackie_chan_face.jpg"
    group_directory = r"./group_photos"
    dataframe = DeepFace.find(face_path, group_directory,
                  detector_backend="retinaface",
                distance_metric="euclidean",
                enforce_detection=False)
    print(dataframe)
    # atgriež sarakstu pandas.dataframe
    
    