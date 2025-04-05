//filtrar Asistente
        document.addEventListener("DOMContentLoaded", function () {
            const input = document.getElementById("searchInputAsistentes");
            const button = document.getElementById("searchButtonAsistentes");
            const tableBody = document.getElementById("tableBodyAsistentes");
        
            button.addEventListener("click", async function () {
                const query = input.value.trim().toLowerCase();
                let url = new URL("http://localhost:8080/api/v1/assistants/filter");
        
                if (!query) return;
        
                // Filtrar según el tipo de entrada
                if (!isNaN(query)) {
                    // Puede ser ID o Teléfono
                    if (query.length <= 6) {
                        url.searchParams.append("id", query);
                    } else {
                        url.searchParams.append("phone", query);
                    }
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
     
//buscar Categoria
document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("searchInputCategory");
    const button = document.getElementById("searchButtonCategory");
    const tableBody = document.getElementById("tableBodyCategory");

    button.addEventListener("click", async function () {
        const query = input.value.trim().toLowerCase();
        let url = new URL("http://localhost:8080/api/v1/category-events/filter");

        if (!query) return;

        // Filtro inteligente con soporte para ID
        if (query === "activo") {
            url.searchParams.append("status", true);
        } else if (query === "inactivo") {
            url.searchParams.append("status", false);
        } else if (!isNaN(query)) {
            url.searchParams.append("id", query); // Asegúrate que el controlador acepte "id"
        } else if (query.length < 30) {
            url.searchParams.append("name", query);
        } else {
            url.searchParams.append("description", query);
        }

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error("❌ Error al obtener categorías");

            const data = await response.json();

            if (data.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="5">😕 No se encontraron categorías</td></tr>`;
                return;
            }

            tableBody.innerHTML = data.map(cat => `
                <tr>
                    <td>${cat.category_id ?? '—'}</td>
                    <td>${cat.name ?? '—'}</td>
                    <td>${cat.description || '—'}</td>
                    <td>${cat.status ? 'Activo' : 'Inactivo'}</td>
                    <td>
                        <button class="edit-btn" data-id="${cat.category_id}">✏️</button>
                        <button class="delete-btn" data-id="${cat.category_id}">🗑️</button>
                    </td>
                </tr>
            `).join("");

        } catch (error) {
            console.error(error);
            alert("🚨 No se pudo filtrar la lista de categorías.");
        }
    });
});

//filtrar ticket 
document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("searchInput");
    const button = document.getElementById("searchButton");
    const tableBody = document.getElementById("tableBody");

    button.addEventListener("click", async function () {
        const query = input.value.trim().toLowerCase();
        let url = new URL("http://localhost:8080/api/v1/tickets/filter");

        // Si no hay búsqueda, trae todos los tickets
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

        // Filtro inteligente: Verificar si la búsqueda corresponde a una fecha o precio
        if (query === "activo") {
            url.searchParams.append("status", true);
        } else if (query === "inactivo") {
            url.searchParams.append("status", false);
        } else if (!isNaN(query)) {  // Filtro por ID Ticket, Evento, o Asistente
            const number = Number(query);
            if (number < 1000) {
                url.searchParams.append("idTicket", number);
            } else if (number >= 1000 && number < 10000) {
                url.searchParams.append("eventId", number);
            } else {
                url.searchParams.append("assistantId", number);
            }
        } else if (query.includes("$") || query.includes(".")) { // Filtro por precio
            // Filtramos por precio, eliminando el "$" y convirtiéndolo a número
            const price = query.replace("$", "").trim();
            if (!isNaN(price)) {
                url.searchParams.append("price", parseFloat(price));
            }
        } else if (isValidDate(query)) { // Filtro por fecha
            // Si la fecha tiene formato válido, se añade como filtro
            url.searchParams.append("fromDate", query);  // Esto es solo un ejemplo, puedes ajustarlo a tus necesidades
        } else {
            url.searchParams.append("seatNumber", query); // Filtro por número de asiento u otro campo
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

    // Función para verificar si el formato de la fecha es válido
    function isValidDate(dateString) {
        const regex = /^\d{4}-\d{2}-\d{2}$/; // Formato YYYY-MM-DD
        const dateRegexWithTime = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/; // Formato YYYY-MM-DD HH:mm:ss
        return regex.test(dateString) || dateRegexWithTime.test(dateString);  // Retorna verdadero si es una fecha válida
    }

    // Función para renderizar la tabla
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
                <td>${ticket.purchaseDate ? new Date(ticket.purchaseDate).toLocaleString() : '—'}</td> <!-- Fecha de Compra -->
            </tr>
        `).join("");
    }
});
