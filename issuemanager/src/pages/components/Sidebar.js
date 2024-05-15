import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styles from './Sidebar.module.css';

function Sidebar() {
    return (
        <div className={styles.container}>
            <div className={styles.sidebar_container}>
                <h2>This is Sidebar</h2>
            </div>
            <div className={styles.sidebar_container}>
                <h2>Project1</h2>
            </div>
            <div className={styles.sidebar_container}>
                <h2>Project2</h2>
            </div>
        </div>
    );
}

export default Sidebar