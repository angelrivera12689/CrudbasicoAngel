// Importar la URL base desde 'constante.js'
import { urlBase } from '/FrontEnd/Js/constante.js'; 

// Definir la URL base para la API de asistentes
const ASISTENTE_API_BASE_URL = `${urlBase}assistants/`; // API base para los asistentes

console.log( ASISTENTE_API_BASE_URL);

// ======================= REGISTRAR ASISTENTE =======================
document.getElementById("asistente-form").addEventListener("submit", async function(event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let nombre = document.getElementById("asistente-nombre").value;
    let email = document.getElementById("asistente-email").value;
    let phone = document.getElementById("asistente-phone").value;

    // Crear el objeto JSON
    let bodyContent = JSON.stringify({
        "name": nombre,
        "email": email,
        "phone": phone
    });

    try {
        let response = await fetch(ASISTENTE_API_BASE_URL, {  // Uso de ASISTENTE_API_BASE_URL
            method: "POST",
            headers: {
                "Accept": "*/*",
                "Content-Type": "application/json"
            },
            body: bodyContent
        });

        let data = await response.text(); 

        if (response.ok) {
            document.getElementById("mensaje").innerText = "Registro exitoso 🎉";
            document.getElementById("asistente-form").reset(); // Limpiar formulario
        } else {
            document.getElementById("mensaje").innerText = "Error al registrar 😢";
        }

        console.log(data); // Para depuración en la consola

    } catch (error) {
        console.error("Error en la petición:", error);
        document.getElementById("mensaje").innerText = "Error de conexión 😢";
    }
});

// ======================= ACTUALIZAR ASISTENTE =======================
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("asistente-update-form").addEventListener("submit", async function(event) {
        event.preventDefault();

        let id = document.getElementById("update-asistente-id").value.trim();
        let nombre = document.getElementById("update-asistente-nombre").value.trim();
        let email = document.getElementById("update-asistente-email").value.trim();
        let phone = document.getElementById("update-asistente-phone").value.trim();

        let mensajeElemento = document.getElementById("mensaje");
        mensajeElemento.innerText = "";

        if (!id || !nombre || !email || !phone) {
            let mensaje = "Todos los campos son obligatorios ⚠️";
            mensajeElemento.innerText = mensaje;
            alert(mensaje);
            return;
        }

        let bodyContent = JSON.stringify({
            "name": nombre,
            "email": email,
            "phone": phone
        });

        let url = `${ASISTENTE_API_BASE_URL}${id}`;  // URL de actualización

        try {
            let response = await fetch(url, {
                method: "PUT",
                headers: {
                    "Accept": "*/*",
                    "Content-Type": "application/json"
                },
                body: bodyContent
            });

            let data = await response.json();
            console.log("📢 Respuesta del servidor:", data);

            if (response.ok) {
                let mensaje = "Asistente actualizado con éxito 🎉";
                mensajeElemento.innerText = mensaje;
                alert(mensaje);
            } else {
                let mensaje = `Error: ${data.message || "No se pudo actualizar"}`;
                mensajeElemento.innerText = mensaje;
                alert(mensaje);
            }

        } catch (error) {
            console.error("❌ Error en la petición:", error);
            let mensaje = "Error de conexión 😢";
            mensajeElemento.innerText = mensaje;
            alert(mensaje);
        }
    });
});

// ======================= FILTRAR ASISTENTE =======================
document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("searchInputAsistentes");
    const button = document.getElementById("searchButtonAsistentes");
    const tableBody = document.getElementById("tableBodyAsistentes");

    button.addEventListener("click", async function () {
        const query = input.value.trim().toLowerCase();
        let url = new URL(`${ASISTENTE_API_BASE_URL}filter`);  // Filtrado de asistentes

        if (!query) return;

        if (!isNaN(query)) {
            if (query.length <= 6) url.searchParams.append("id", query);
            else url.searchParams.append("phone", query);
        } else if (query.includes("@")) {
            url.searchParams.append("email", query);
        } else if (query === "activo") {
            url.searchParams.append("status", true);
        } else if (query === "inactivo") {
            url.searchParams.append("status", false);
        } else {
            url.searchParams.append("name", query);
        }

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error("❌ Error al obtener datos");

            const data = await response.json();

            if (data.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="7">😕 No se encontraron asistentes</td></tr>`;
                return;
            }

            tableBody.innerHTML = data.map(asistente => `
                <tr>
                    <td>${asistente.id}</td>
                    <td>${asistente.name}</td>
                    <td>${asistente.email}</td>
                    <td>${asistente.phone || '—'}</td>
                    <td>${asistente.status ? 'Activo' : 'Inactivo'}</td>
                    <td>
                        <button class="edit-btn" data-id="${asistente.id}">✏️</button>
                        <button class="delete-btn" data-id="${asistente.id}">🗑️</button>
                    </td>
                </tr>
            `).join("");
        } catch (error) {
            console.error(error);
            alert("🚨 No se pudo filtrar la lista de asistentes.");
        }
    });
});

// ======================= ELIMINAR ASISTENTE =======================
document.getElementById("asistente-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita el comportamiento por defecto del formulario

    const id = document.getElementById("asistente-id-eliminate").value.trim();
    const mensaje = document.getElementById("mensaje");

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`${ASISTENTE_API_BASE_URL}${id}`, {
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Asistente con ID ${id} eliminado correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("asistente-delete-form").reset();
            alert("✅ Asistente eliminado correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar el asistente"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});
