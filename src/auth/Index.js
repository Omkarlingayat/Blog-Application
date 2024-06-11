// Is user logged in
export const isLoggedIn = () => {
    let data = localStorage.getItem("data");
    if(data == null){
        return false;
    }
    else{
        return true;
    }
};

// doLogin => data => set to local storage
export const doLogin = (data,next) => {
    localStorage.setItem("data", JSON.stringify(data));
    next()
};

// doLogOut => remove data from local storage
export const doLogOut = (next) =>{
    localStorage.removeItem("data");
    next()
}

// get current user details
export const getCurrentUserDetails = () => {
    if(isLoggedIn()){
        return JSON.parse(localStorage.getItem("data")).user; 
    }
    else{
        return undefined;
    }
}

// get token
export const getToken = ()=>{
    if(isLoggedIn()){
        return JSON.parse(localStorage.getItem("data")).jwtToken
    }
    else{
        return null;
    }
}