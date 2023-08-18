function alert(status ,message) {
    // Sau khi xóa thành công, thêm thông báo vào HTML
    const alertMessage = document.createElement("span");
    alertMessage.className = "alert alert-" + status + " notification";
    alertMessage.role = "alert";
    alertMessage.textContent = message;

    // Tạo phần tử hình ảnh (img)
    const imgElement = document.createElement("img");
    if (status === "success") {
        imgElement.src = "https://img.icons8.com/?size=512&id=pIPl8tqh3igN&format=png";
    } else {
        imgElement.src = "https://img.icons8.com/?size=512&id=fYgQxDaH069W&format=png";
    }
    imgElement.alt = "Icon";
    imgElement.style.width = "24px"; // Đặt chiều rộng hình ảnh
    imgElement.style.marginLeft = "10px";



    // Thêm hình ảnh và nội dung thông báo vào span
    alertMessage.appendChild(imgElement);

    document.body.appendChild(alertMessage);

    // Tự động ẩn thông báo sau 4,5 giây
    setTimeout(function() {
        document.body.removeChild(alertMessage);
    }, 4500);
}

// check file avatar
function validateFileSize() {
    const fileInput = document.getElementById('avatar');
    const maxSize = 1 * 1024 * 1024; // 1MB

    if (fileInput.files.length > 0) {
        const fileSize = fileInput.files[0].size;
        if (fileSize > maxSize) {
            alert("danger" ,'Fail! ,File size exceeds 1MB. Please select a smaller file.');
            fileInput.value = ''; // Clear the selected file
        }
    }
}


