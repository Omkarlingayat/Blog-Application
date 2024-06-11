import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import About from "./pages/About";
import Services from "./pages/Services";
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from "react-toastify";
import PrivateRoute from "./components/PrivateRoute";
import ProfileInfo from "./pages/user-routes/ProfileInfo";
import UserDashboard from "./pages/user-routes/UserDashboard";
import PostPage from "./pages/PostPage";
import UserProvider from "./context/userProvider";
import Categories from "./pages/Categories";

function App() {
  return (

    <UserProvider>
      <BrowserRouter>
        <ToastContainer />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/about" element={<About />} />
          <Route path="/services" element={<Services />} />
          <Route path="/post/:postId" element={<PostPage />} />
          <Route path="/categories/:categorieId" element={<Categories/>}/>

          <Route path="/user" element={<PrivateRoute />}>
            <Route path="dashboard" element={<UserDashboard />} />
            <Route path="profile-info" element={<ProfileInfo />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </UserProvider>

);
}

export default App;
