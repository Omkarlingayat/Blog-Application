import { myAxios } from "./Helper";

//-------- signup url ---------------
export const signUp = (user)=>{
    return myAxios
    .post("/auth/signup",user)
    .then((response)=> response.data)
};

//-------------- login url ----------------
export const loginUser = (loginDetails)=>{
    return myAxios
    .post("/auth/login",loginDetails)
    .then(response=>{return response.data})
};