# Family Trees

Welcome to the **GitHub repository** for the **UMCP VSA Family Trees visualization project**! This interactive web application is designed to represent the ACE (Anh Chi Em) family trees of the University of Maryland, College Park Vietnamese Student Association (UMCP VSA), showcasing the intricate relationships between members across different generations.

## Quick Links

- **Family Tree Visualization Website:** [http://www.umcpvsafamilytrees.com](http://www.umcpvsafamilytrees.com)
- **Google Form for Member Submission:** [https://forms.gle/dxmU92HthhTfj8f79](https://forms.gle/dxmU92HthhTfj8f79)

## Overview

The **UMCP VSA Family Trees project** is an innovative web application enabling users to navigate through various family trees. It features dynamic, zoomable visualizations of family branches and provides a search function to effortlessly find specific members by name.

## Technologies Used

This project is built on a diverse tech stack for both front-end presentation and back-end data management:

### Front-end:
- **HTML:** Structures the web page.
- **CSS:** Styles the visual elements for a user-friendly experience.
- **JavaScript (JS):** Adds interactivity to the web application.
- **D3.js:** Generates dynamic, interactive data visualizations in the web browser.

### Back-end:
- **Java API:** Utilizes RESTful endpoints with Spring Boot for server-side logic.
- **Gradle:** Manages dependencies and builds the Java project.

### Data Management:
- **Google Forms:** Collects member data.
- **Google Sheets API:** Stores the form submissions in an organized manner.

### Deployment:
- **Google Cloud Platform (GCP):** Hosts the application using App Engine, ensuring scalability and reliability.
- **Node.js:** Serves the front-end website, interfacing with the Java API to fetch family tree data.

## How It Works

- **Data Collection:** Members submit their information through a Google Form.
- **Data Storage:** Submissions are automatically stored in Google Sheets.
- **Back-end Processing:** A Java API reads the data from Google Sheets, organizes it into family trees, and provides RESTful endpoints for data access.
- **Front-end Visualization:** The website fetches the tree data and uses D3.js to render interactive, dynamic family tree visualizations.
- **User Interaction:** Users can explore the family trees, zoom in/out, and search for specific members by name.
