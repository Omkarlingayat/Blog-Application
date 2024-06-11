import { myAxios, privateAxios } from "./Helper";
// after login you can use privateAxios for accessing token.

export const createPost = (postData) => {
    //console.log(postData)
    return privateAxios.post(`/api/post/user/${postData.userId}/category/${postData.categoryId}/create`,postData).then((response)=>response.data)
};

// get all posts
export const loadAllPosts = (pageNumber, pageSize) => {
    return myAxios.get(`/api/post/get?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=addedDate&sortDir=desc`).then((response) => response.data)
};

// load single post for given id
export const loadPost = (postId) => {
    return myAxios.get("/api/post/get/"+postId).then((response) => response.data);
};

// add comment url
export const createComment = (userId, postId, comment)=>{
    return privateAxios.post(`/api/comment/user/${userId}/post/${postId}/create`,comment).then((response)=>response.data)
};

// upload image
export const uploadImage = (postId, file)=>{
    return privateAxios.post(`/api/post/image/upload/${postId}`,file).then((response)=>response.data)
};
///image/upload/{postId}

// get all posts by category wise
export const loadPostsCategorywise = (categoryId)=>{
    return privateAxios.get(`/api/pos//get/category/${categoryId}`).then((res)=>res.data)
}