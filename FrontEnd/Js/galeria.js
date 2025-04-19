// Project data
const projects = [
    {
      id: 1,
      title: "Diseño de sala de estar moderna",
      description: "Espacio minimalista con elementos naturales y tonos neutros.",
      images: ["path/to/large-image1.jpg", "path/to/detail1.jpg"],
      details: "Este proyecto incorpora luz natural, plantas y materiales orgánicos para crear un ambiente acogedor y sereno."
    },
    {
      id: 2,
      title: "Comedor contemporáneo",
      description: "Diseño elegante con elementos de madera y tonos claros.",
      images: ["path/to/large-image2.jpg", "path/to/detail2.jpg"],
      details: "Un espacio funcional que combina estética y practicidad para disfrutar de comidas en familia."
    },
    // More projects...
  ];
  
  function showProjectDetails(projectId) {
    const modal = document.getElementById("projectModal");
    const projectDetailsDiv = document.getElementById("projectDetails");
    const project = projects.find(p => p.id === projectId);
    
    if (project) {
      // Create HTML content for the modal
      let content = `
        <h3>${project.title}</h3>
        <p class="project-description">${project.description}</p>
        <div class="project-images">
      `;
      
      // Add images to the content
      project.images.forEach(img => {
        content += `<img src="${img}" alt="${project.title}">`;
      });
      
      content += `
        </div>
        <p class="project-details">${project.details}</p>
      `;
      
      projectDetailsDiv.innerHTML = content;
      modal.style.display = "block";
    }
  }
  
  function closeModal() {
    document.getElementById("projectModal").style.display = "none";
  }
  
  // Close modal when clicking outside of content
  window.onclick = function(event) {
    const modal = document.getElementById("projectModal");
    if (event.target === modal) {
      modal.style.display = "none";
    }
  }