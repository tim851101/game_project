//解決顯示product - description問題
const listButton = document.querySelector('.btn-list');
const gridButton = document.querySelector('.btn-grid-3');

listButton.addEventListener('click', () => {
    const productDescriptions = document.querySelectorAll('.product-description');
    productDescriptions.forEach(description => {
        description.style.display = 'block';
    });
});

gridButton.addEventListener('click', () => {
    const productDescriptions = document.querySelectorAll('.product-description');
    productDescriptions.forEach(description => {
        description.style.display = 'none';
    });
});