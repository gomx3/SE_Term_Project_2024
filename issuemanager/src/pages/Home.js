import React, { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import './home.css';
import Projectinfo from './projectpage';

function Home() {
  const location = useLocation();
  const navigate = useNavigate();
  const { state } = location;
  const initialMemberId = state ? state.memberId : null;
  const initialRole = state ? state.role : null;
  const [memberId, setMemberId] = useState(initialMemberId);
  const [role, setRole] = useState(initialRole);
  const [error, setError] = useState('');

  const handleLogout = async () => {
    try {
      const response = await fetch('/members/logout', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
      });

      const data = await response.json();

      console.log('Logout Response:', data);

      if (data.isSuccess) {
        alert('Logout successful!');
        setMemberId(null);
        setRole(null);
        navigate('/signin');
      } else {
        setError(data.message || 'Logout failed.');
      }
    } catch (error) {
      console.error('Logout Error:', error);
      setError('An error occurred during logout.');
    }
  };

  const [selectedProject, setSelectedProject] = useState(null);

  return (
    <div className="home-container">
      <header className="home-header">
        <h2>IssueManager</h2>
        {memberId ? (
          <div>
            Welcome, {memberId} ({role})! <button onClick={handleLogout} className="logout-link">Logout</button>
            {error && <div className="error-message">{error}</div>}
          </div>
        ) : (
          <Link to="/signin">Sign In</Link>
        )}
      </header>
      <main className="home-main">
        <Catalog setSelectedProject={setSelectedProject} />
        <Content selectedProject={selectedProject} />
      </main>
    </div>
  );
}

function Catalog({ setSelectedProject }) {
  const [showInput, setShowInput] = useState(false);
  const [projects, setProjects] = useState([]);
  const [newProjectName, setNewProjectName] = useState('');

  const toggleInput = () => {
    setShowInput(!showInput);
    if (!showInput) {
      setNewProjectName('');
    }
  };

  const handleInputChange = (event) => {
    setNewProjectName(event.target.value);
  };

  const cancelInput = () => {
    toggleInput();
  };

  const addProject = () => {
    if (newProjectName.trim() === '') {
      alert('Please enter a project name.');
      return;
    }
    setProjects([...projects, newProjectName]);
    toggleInput();
  };

  return (
    <aside className="home-catalog">
      <h3>Projects</h3>
      <ul>
        {projects.map((project, index) => (
          <li key={index} className="project-item" onClick={() => setSelectedProject(project)}>
            {project}
          </li>
        ))}
      </ul>
      {showInput && (
        <div className="input-container">
          <div className="input-wrapper">
            <input type="text" value={newProjectName} onChange={handleInputChange} />
            <button className="proj-cancel" onClick={cancelInput}>Cancel</button>
            <button className="proj-add" onClick={addProject}>Add</button>
          </div>
        </div>
      )}
      {!showInput && (
        <button className="add-button" onClick={toggleInput}>Add new Project</button>
      )}
    </aside>
  );
}

function Content({ selectedProject }) {
  return (
    <section className="home-content">
      <Projectinfo project={selectedProject} />
    </section>
  );
}

export default Home;