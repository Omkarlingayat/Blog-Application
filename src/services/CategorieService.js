import { myAxios } from "./Helper";

//---------- get all categories ---------------
export const loadAllCategories = () => {
    return myAxios.get("/auth/get").then(response => {return response.data})
};