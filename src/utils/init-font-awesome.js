function initFontAwesome() {
    if (!window.fontAwesomeRequested) {
        window.fontAwesomeRequested = true;

        const cssLink = document.createElement('link');
        cssLink.href = 'fontawesome/css/custom-icons.css';
        cssLink.rel = 'stylesheet';
        document.head.appendChild(cssLink);
    }
}

export default initFontAwesome;
