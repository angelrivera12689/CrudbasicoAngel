
// Importar la URL base desde 'constante.js'
import { urlBase } from '/FrontEnd/Js/constante.js';  // Asegúrate de que la ruta sea correcta

// Definir la URL base para los tickets
const TICKET_API_BASE_URL = `${urlBase}tickets/`;  // Concatenamos la URL base con el endpoint de tickets

console.log( TICKET_API_BASE_URL);  // Verifica que la URL esté correctamente formada

// ======================= REGISTRAR TICKET =======================
document.getElementById("ticket-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Evita que la página se recargue

    // Capturar los valores del formulario
    let eventId = parseInt(document.getElementById("ticket-event-id").value.trim());
    let assistantId = parseInt(document.getElementById("ticket-assistant-id").value.trim());
    let price = parseFloat(document.getElementById("ticket-price").value.trim());
    let seatNumber = document.getElementById("ticket-seat-number").value.trim();

    // Validar que los campos no estén vacíos y sean correctos
    if (isNaN(eventId) || isNaN(assistantId) || isNaN(price) || !seatNumber) {
        alert("Todos los campos son obligatorios y deben ser válidos.");
        return;
    }

    let bodyContent = JSON.stringify({
        eventId: eventId,
        assistantId: assistantId,
        price: price,
        seatNumber: seatNumber
    });

    let headersList = {
        "Accept": "*/*",
        "User-Agent": "Thunder Client (https://www.thunderclient.com)",
        "Content-Type": "application/json"
    };

    try {
        let response = await fetch(TICKET_API_BASE_URL, {  // Usamos la URL base de tickets
            method: "POST",
            body: bodyContent,
            headers: headersList
        });

        if (!response.ok) {
            throw new Error("Error en la solicitud: " + response.statusText);
        }

        let data = await response.json();
        console.log("Ticket registrado:", data);
        alert("Ticket registrado con éxito");
        document.getElementById("ticket-form").reset(); // Limpia el formulario
    } catch (error) {
        console.error("Error al registrar el ticket:", error);
        alert("Error al registrar el ticket.");
    }
});

// ======================= ACTUALIZAR TICKET =======================
document.getElementById("ticket-update-form").addEventListener("submit", async function(event) {
    event.preventDefault();

    let id = document.getElementById("ticket-id-update").value.trim();
    let eventId = document.getElementById("ticket-event-id-update").value.trim();
    let assistantId = document.getElementById("ticket-assistant-id-update").value.trim();
    let price = document.getElementById("ticket-price-update").value.trim();
    let seatNumber = document.getElementById("ticket-seat-number-update").value.trim();

    if (!id || !eventId || !assistantId || !price || !seatNumber) {
        alert("Todos los campos son obligatorios ⚠️");
        return;
    }

    if (parseFloat(price) <= 0) {
        alert("El precio debe ser mayor a 0 💰");
        return;
    }

    let bodyContent = JSON.stringify({
        "eventId": parseInt(eventId),
        "assistantId": parseInt(assistantId),
        "price": parseFloat(price),
        "seatNumber": seatNumber
    });

    try {
        let response = await fetch(`${TICKET_API_BASE_URL}${id}`, {  // Usamos la URL base de tickets y el ID del ticket a actualizar
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: bodyContent
        });

        let data = await response.json();

        if (response.ok) {
            alert("✅ Ticket actualizado con éxito 🎉");
            document.getElementById("ticket-update-form").reset();
        } else {
            alert(`❌ Error: ${data.message || "No se pudo actualizar el ticket"}`);
        }

        console.log("📢 Respuesta del servidor:", data);

    } catch (error) {
        console.error("❌ Error en la petición:", error);
        alert("❌ Error de conexión");
    }
});

// ======================= FILTRAR TICKETS =======================
document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("searchInput");
    const button = document.getElementById("searchButton");
    const tableBody = document.getElementById("tableBody");

    button.addEventListener("click", async function () {
        const query = input.value.trim().toLowerCase();
        let url = new URL(`${TICKET_API_BASE_URL}filter`);

        if (!query) {
            try {
                const response = await fetch(url);
                const data = await response.json();
                renderTable(data);
            } catch (error) {
                console.error(error);
                alert("🚨 No se pudieron cargar los tickets.");
            }
            return;
        }

        if (query === "activo") {
            url.searchParams.append("status", true);
        } else if (query === "inactivo") {
            url.searchParams.append("status", false);
        } else if (!isNaN(query)) {
            const number = Number(query);
            if (number < 1000) url.searchParams.append("idTicket", number);
            else if (number < 10000) url.searchParams.append("eventId", number);
            else url.searchParams.append("assistantId", number);
        } else if (query.includes("$") || query.includes(".")) {
            const price = query.replace("$", "").trim();
            if (!isNaN(price)) url.searchParams.append("price", parseFloat(price));
        } else if (isValidDate(query)) {
            url.searchParams.append("fromDate", query);
        } else {
            url.searchParams.append("seatNumber", query);
        }

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error("❌ Error al obtener tickets");

            const data = await response.json();
            renderTable(data);
        } catch (error) {
            console.error(error);
            alert("🚨 No se pudo filtrar la lista de tickets.");
        }
    });

    function isValidDate(dateString) {
        const regex = /^\d{4}-\d{2}-\d{2}$/;
        const dateTimeRegex = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;
        return regex.test(dateString) || dateTimeRegex.test(dateString);
    }

    function renderTable(data) {
        if (data.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="7">😕 No se encontraron tickets</td></tr>`;
            return;
        }

        tableBody.innerHTML = data.map(ticket => `
            <tr>
                <td>${ticket.idTicket ?? '—'}</td>
                <td>${ticket.event?.idEvent ?? '—'}</td>
                <td>${ticket.assistant?.id_assistant ?? '—'}</td>
                <td>$${ticket.price?.toFixed(2) ?? '—'}</td>
                <td>${ticket.seatNumber ?? '—'}</td>
                <td>${ticket.status ? 'Activo' : 'Inactivo'}</td>
                <td>${ticket.purchaseDate ? new Date(ticket.purchaseDate).toLocaleString() : '—'}</td>
            </tr>
        `).join("");
    }
});

// ======================= ELIMINAR TICKET =======================
document.getElementById("ticket-delete-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Previene la recarga

    const id = document.getElementById("ticket-id-delete").value.trim();
    const mensaje = document.getElementById("mensaje"); // Asegúrate de tener este div en el HTML

    if (!id) {
        mensaje.innerText = "⚠️ Por favor ingresa un ID válido.";
        mensaje.style.color = "orange";
        return;
    }

    try {
        const response = await fetch(`${TICKET_API_BASE_URL}${id}`, {  // Usamos la URL base de tickets y el ID del ticket a eliminar
            method: "DELETE",
            headers: {
                "Accept": "*/*"
            }
        });

        if (response.ok) {
            mensaje.innerText = `✅ Ticket con ID ${id} eliminado correctamente.`;
            mensaje.style.color = "green";
            document.getElementById("ticket-delete-form").reset();
            alert("✅ Ticket eliminado correctamente.");
        } else {
            const data = await response.json();
            mensaje.innerText = `❌ Error al eliminar: ${data.message || "No se pudo eliminar el ticket"}`;
            mensaje.style.color = "red";
        }
    } catch (error) {
        console.error("❌ Error de conexión:", error);
        mensaje.innerText = "⚠️ Error de conexión al eliminar.";
        mensaje.style.color = "red";
    }
});
