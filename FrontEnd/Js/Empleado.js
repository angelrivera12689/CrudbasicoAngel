import { urlBase } from '/FrontEnd/js/constante.js';
const API_URL = `${urlBase}employee/`;

document.addEventListener("DOMContentLoaded", function () {
  
  async function cargarEmpleados(filtros = {}) {
    try {
      let url = API_URL;

      if (Object.keys(filtros).length > 0) {
        const queryString = new URLSearchParams(filtros).toString();
        url += `filter?${queryString}`;
      }

      let response = await fetch(url);
      if (!response.ok) {
        throw new Error("Error al obtener empleados");
      }

      let empleados = await response.json();
      empleados = empleados.slice(0, 10);

      console.log("Empleados obtenidos:", empleados);

      const employeeList = document.getElementById("employee-list");
      employeeList.innerHTML = "";

      empleados.forEach(empleado => {
        const li = document.createElement("li");
        li.classList.add("employee-card");

        const firstName = empleado.first_name || empleado.firstName;
        const lastName = empleado.last_name || empleado.lastName;
        const phoneNumber = empleado.phone_number || empleado.phoneNumber;
        const imageUrl = empleado.image_url || empleado.imageUrl || 'https://via.placeholder.com/100';

        li.innerHTML = `
          <img src="${imageUrl}" alt="Foto">
          <strong>${firstName} ${lastName}</strong>
          <div class="phone-number">${phoneNumber}</div>
          <div class="button-group">
            <button class="btn btn-warning btn-sm editar-btn" data-id="${empleado.id_employee}">Editar</button>
            <button class="btn btn-danger btn-sm eliminar-btn" data-id="${empleado.id_employee}">Eliminar</button>
          </div>
        `;
        employeeList.appendChild(li);
      });

      document.querySelectorAll('.editar-btn').forEach(btn => {
        btn.addEventListener('click', function () {
          const id = this.dataset.id;
          editarEmpleado(id);
        });
      });

      document.querySelectorAll('.eliminar-btn').forEach(btn => {
        btn.addEventListener('click', function () {
          const id = this.dataset.id;
          eliminarEmpleado(id);
        });
      });

    } catch (error) {
      console.error("Error al cargar empleados:", error);
      alert("Error al cargar los empleados. Revisa la consola.");
    }
  }

  document.getElementById("employee-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    const firstName = document.getElementById("employee-first-name").value;
    const lastName = document.getElementById("employee-last-name").value;
    const address = document.getElementById("employee-address").value;
    const phoneNumber = document.getElementById("employee-phone").value;
    const imageUrl = document.getElementById("employee-image-url").value;

    const newEmployee = {
      firstName,
      lastName,
      address,
      phoneNumber,
      imageUrl
    };

    try {
      let response = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newEmployee)
      });

      if (response.ok) {
        alert("Empleado registrado con éxito");
        cargarEmpleados();
        document.getElementById("employee-form").reset();
      } else {
        throw new Error("Error al registrar empleado");
      }
    } catch (error) {
      console.error("Error al registrar empleado:", error);
      alert("Error al registrar el empleado. Revisa la consola.");
    }
  });

  async function editarEmpleado(id) {
    try {
      let response = await fetch(`${API_URL}${id}`);
      if (!response.ok) {
        throw new Error("Empleado no encontrado");
      }

      let empleado = await response.json();
      console.log("Empleado a editar:", empleado);

      document.getElementById("update-employee-first-name").value = empleado.first_name || empleado.firstName;
      document.getElementById("update-employee-last-name").value = empleado.last_name || empleado.lastName;
      document.getElementById("update-employee-address").value = empleado.address;
      document.getElementById("update-employee-phone").value = empleado.phone_number || empleado.phoneNumber;
      document.getElementById("update-employee-image-url").value = empleado.image_url || empleado.imageUrl || '';

      document.getElementById("updateEmployeeModal").style.display = "flex";
      document.getElementById("update-employee-form").dataset.employeeId = id;
    } catch (error) {
      console.error("Error al cargar el empleado para editar:", error);
      alert("Error al cargar el empleado para editar.");
    }
  }

  document.getElementById("update-employee-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    const id = document.getElementById("update-employee-form").dataset.employeeId;
    const firstName = document.getElementById("update-employee-first-name").value;
    const lastName = document.getElementById("update-employee-last-name").value;
    const address = document.getElementById("update-employee-address").value;
    const phoneNumber = document.getElementById("update-employee-phone").value;
    const imageUrl = document.getElementById("update-employee-image-url").value;

    const updatedEmployee = {
      firstName,
      lastName,
      address,
      phoneNumber,
      imageUrl
    };

    try {
      let response = await fetch(`${API_URL}${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedEmployee)
      });

      if (response.ok) {
        alert("Empleado actualizado con éxito");
        cargarEmpleados();
        document.getElementById("updateEmployeeModal").style.display = "none";
      } else {
        throw new Error("Error al actualizar empleado");
      }
    } catch (error) {
      console.error("Error al actualizar empleado:", error);
      alert("Error al actualizar el empleado.");
    }
  });

  document.getElementById("closeUpdateModalBtn").addEventListener("click", function () {
    document.getElementById("updateEmployeeModal").style.display = "none";
  });

  async function eliminarEmpleado(id) {
    if (confirm("¿Estás seguro de que deseas eliminar este empleado?")) {
      try {
        let response = await fetch(`${API_URL}${id}`, { method: "DELETE" });

        if (response.ok) {
          alert("Empleado eliminado con éxito");
          cargarEmpleados();
        } else {
          throw new Error("Error al eliminar empleado");
        }
      } catch (error) {
        console.error("Error al eliminar empleado:", error);
        alert("Error al eliminar el empleado.");
      }
    }
  }

  // Aquí es donde agregamos el filtro sin romper nada
  document.getElementById("filter-employee-form").addEventListener("submit", function (event) {
    event.preventDefault(); // Evitar recargar página

    const filtros = {
      first_name: document.getElementById("filter-first-name").value.trim(),
      last_name: document.getElementById("filter-last-name").value.trim(),
      address: document.getElementById("filter-address").value.trim(),
      phone_number: document.getElementById("filter-phone-number").value.trim(),
      status: document.getElementById("filter-status").value.trim()
    };

    // Limpiar filtros vacíos (para no enviar parámetros vacíos)
    Object.keys(filtros).forEach(key => {
      if (filtros[key] === '') {
        delete filtros[key];
      }
    });

    cargarEmpleados(filtros);
  });

  cargarEmpleados();
});
