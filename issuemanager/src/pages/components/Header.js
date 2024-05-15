import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styles from './Header.module.css';

function Header() {
    return (
        <nav className={styles.header}>
            <div className={styles.head_container}>
                <h2>issueManager</h2>
                <div className={styles.signup_link}>
                  <p>
                    <Link to="/signup">Sign Up</Link>
                  </p>
                </div>
            </div>
        </nav>
    );
}

export default Header