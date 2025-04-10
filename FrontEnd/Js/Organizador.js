// Definir la URL base para la API de organizadores
import { urlBase }  from '/FrontEnd/Js/constante.js'; // Importar la URL base

const API_URL_ORGANIZER = `${urlBase}organizer/`; // Concatenar la URL base con la ruta del recurso "organizer"

console.log(API_URL_ORGANIZER); // Verifica que la URL esté correctamente formada


// Registrar organizador
document.getElementById("organizer-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let name = document.getElementById("organizador-nombre").value.trim();
    let phone = document.getElementById("organizador-telefono").value.trim();
    let email = document.getElementById("organizador-email").value.trim();

    // Validar que los campos no estén vacíos
    if (!name || !phone || !email) {
        alert("Todos los campos son obligatorios.");
        return;
    }

    let bodyContent = JSON.stringify({
        name: name,
        phone: phone,
        email: email
    });

    let headersList = {
        "Accept": "*/*",
        "Content-Type": "application/json"
    };

    try {
        let response = await fetch(API_URL_ORGANIZER, {
            method: "POST",
            body: bodyContent,
            headers: headersList
        });

        if (!response.ok) {
            throw new Error("Error en la solicitud: " + response.statusText);
        }

        let data = await response.json();
        console.log("Organizador registrado:", data);
        alert("Organizador registrado con éxito");
        document.getElementById("organizer-form").reset(); // Limpia el formulario
    } catch (error) {
        console.error("Error al registrar el organizador:", error);
        alert("Error al registrar el organizador.");
    }
});

// ======================= ACTUALIZAR ORGANIZADOR =======================
document.getElementById("organizador-update-form").addEventListener("submit", async function(event) {
    event.preventDefault();

    let id = document.getElementById("organizador-id-update").value.trim();
    let nombre = document.getElementById("organizador-nombre-update").value.trim();
    let telefono = document.getElementById("organizador-telefono-update").value.trim();
    let email = document.getElementById("organizador-email-update").value.trim();

    if (!id || !nombre || !telefono || !email) {
        alert("Todos los campos son obligatorios ⚠️");
        return;
    }

    let bodyContent = JSON.stringify({
        "name": nombre,
        "phone": telefono,
        "email": email
    });

    try {
        let response = await fetch(`${API_URL_ORGANIZER}${id}`, { // Uso de API_URL_ORGANIZER correctamente
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: bodyContent
        });

        let data = await response.json();

        if (response.ok) {
            alert("✅ Organizador actualizado con éxito 🎉");
            document.getElementById("organizador-update-form").reset();
        } else {
            alert(`❌ Error: ${data.message || "No se pudo actualizar el organizador"}`);
        }

        console.log("📢 Respuesta del servidor:", data);

    } catch (error) {
        console.error("❌ Error en la petición:", error);
        alert("❌ Error de conexión");
    }
});

// Eliminar organizador
document.getElementById("organizador-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita recargar la página

    const id = document.getElementById("organizador-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate que exista en el HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`${API_URL_ORGANIZER}${id}`, { // Uso de API_URL_ORGANIZER correctamente
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Organizador con ID ${id} eliminado correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("organizador-delete-form").reset();
            alert("✅ Organizador eliminado correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar el organizador"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});
