//新增圖片
const form = document.getElementById('upload-form');
let successMessageDisplayed = false;

form.addEventListener('submit', (event) => {
    event.preventDefault();

    const fileInput = document.getElementById('file');
    const pdNoInput = document.getElementById('pdNo');

    const formData = new FormData();
    for (let i = 0; i < fileInput.files.length; i++) {
        const file = fileInput.files[i];
        // 檢查是否為圖片文件
        if (!file.type.startsWith("image/")) {
            Swal.fire(`第 ${i + 1} 個檔案不是圖片檔案`);
            return;
        }
        formData.append('files', file);
    }
    formData.append('pdNo', pdNoInput.value);

    fetch('/pic/product-pic', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!successMessageDisplayed) {
                const successMessage = document.createElement('p');
                successMessage.textContent = '圖片存進資料庫成功';
                successMessage.style.color = 'red';
                form.appendChild(successMessage);
                successMessageDisplayed = true;
            }
        })
        .catch(error => {
            console.error(error);
            alert(error.message);
        });
});
