import React from 'react';
import { Routes , Route } from "react-router-dom"
import Home from './pages/Home';
import SignIn from './pages/SignIn';
import SignUp from './pages/SignUp';
import EditIssue from './pages/editIssue/EditIssue';

class App extends React.Component {

  constructor(props){
    super(props);
    this.state={};
  }

render() {
    return <Routes>
      <Route path ="/" element = {<Home />}></Route>
      <Route path ="/signin" element = {<SignIn />}></Route>
      <Route path ="/signup" element = {<SignUp />}></Route>
      <Route path ="/editissue" element = {<EditIssue />}></Route>
    </Routes>
  };
}
export default App;