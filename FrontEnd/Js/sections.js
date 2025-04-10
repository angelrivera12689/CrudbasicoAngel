document.addEventListener("DOMContentLoaded", function () {
    // Obtener todas las secciones
    const secciones = ["Crear", "Filtrar", "Actualizar", "Eliminar"];

    // Ocultar todas las secciones al cargar la página
    secciones.forEach(id => {
        const seccion = document.getElementById(id);
        if (seccion) {
            seccion.style.display = "none";  // Ocultar las secciones inicialmente
        }
    });

    // Función para mostrar solo la sección seleccionada
    function mostrarSeccion(id) {
        secciones.forEach(sec => {
            const seccion = document.getElementById(sec);
            if (seccion) {
                seccion.style.display = "none"; // Oculta todas las secciones
            }
        });
        const seleccionada = document.getElementById(id);
        if (seleccionada) {
            seleccionada.style.display = "block"; // Muestra la sección seleccionada
        }
    }

    // Asignar eventos a los botones solo si existen
    const btnCrear = document.getElementById("btnCrear");
    const btnFiltrar = document.getElementById("btnFiltrar");
    const btnActualizar = document.getElementById("btnActualizar");
    const btnEliminar = document.getElementById("btnEliminar");

    // Verificar si los botones existen antes de agregarles eventos
    if (btnCrear) {
        btnCrear.addEventListener("click", function () {
            mostrarSeccion("Crear");
        });
    }

    if (btnFiltrar) {
        btnFiltrar.addEventListener("click", function () {
            mostrarSeccion("Filtrar");
        });
    }

    if (btnActualizar) {
        btnActualizar.addEventListener("click", function () {
            mostrarSeccion("Actualizar");
        });
    }

    if (btnEliminar) {
        btnEliminar.addEventListener("click", function () {
            mostrarSeccion("Eliminar");
        });
    }
});



document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll('.btn-toggle-filter'); // Todos los botones
    const sections = document.querySelectorAll('section[id^="filtrar-"]'); // Todas las secciones con id que empieza con 'filtrar-'

    // Función para ocultar todas las secciones
    function hideAllSections() {
        sections.forEach(section => {
            section.style.display = 'none'; // Ocultar todas las secciones
        });
    }

    // Agregar el evento de clic a cada botón
    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const sectionToShow = document.getElementById(button.getAttribute('data-section')); // Obtener la sección correspondiente
            hideAllSections(); // Ocultar todas las secciones
            sectionToShow.style.display = 'block'; // Mostrar solo la sección seleccionada
        });
    });

    // Mostrar la primera sección por defecto al cargar la página (opcional)
    if (sections.length > 0) {
        hideAllSections(); // Asegura que se oculten todas las secciones inicialmente
        sections[0].style.display = 'block'; // Mostrar la primera sección
    }
});
 
 
document.addEventListener("DOMContentLoaded", function () {
    // Obtener todos los formularios y ocultarlos excepto el primero
    const formularios = document.querySelectorAll("form");
    formularios.forEach((form, index) => {
        if (index !== 0) {
            form.classList.add("hidden");
        }
    });

    // Función para mostrar el formulario seleccionado
    function mostrarFormulario(id) {
        formularios.forEach(form => form.classList.add("hidden")); // Oculta todos
        document.getElementById(id).classList.remove("hidden"); // Muestra el seleccionado
    }

    // Asignar eventos a los botones
    document.querySelectorAll(".btn-toggle").forEach(button => {
        button.addEventListener("click", function () {
            const formId = this.getAttribute("data-form");
            mostrarFormulario(formId);
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll('.btn-toggle'); // Todos los botones
    const forms = document.querySelectorAll('form[id$="-update-form"]'); // Formularios con id que termina en '-update-form'

    // Función para ocultar todas las secciones
    function hideAllForms() {
        forms.forEach(form => {
            form.classList.add('hidden'); // Ocultar todas las secciones
        });
    }

    // Agregar el evento de clic a cada botón
    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const formToShow = document.getElementById(button.getAttribute('data-form')); // Obtener la sección correspondiente
            hideAllForms(); // Ocultar todas las secciones
            formToShow.classList.remove('hidden'); // Mostrar solo la sección seleccionada
        });
    });

    // Mostrar la primera sección por defecto al cargar la página (opcional)
    if (forms.length > 0) {
        hideAllForms(); // Asegura que se oculten todas las secciones inicialmente
        forms[0].classList.remove('hidden'); // Mostrar el primer formulario
    }
});

 