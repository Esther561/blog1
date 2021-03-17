window.addEventListener('load', () => {
    $('.time').each(() => {
        $(this).text(Date.parse($(this).text()).toLocaleString('en-NZ'));
    })
});