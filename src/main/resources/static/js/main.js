function alert(status ,message) {
    // Sau khi xóa thành công, thêm thông báo vào HTML
    const alertMessage = document.createElement("span");
    alertMessage.className = "alert alert-" + status + " notification";
    alertMessage.role = "alert";
    alertMessage.textContent = message;

    // Tạo phần tử hình ảnh (img)
    const imgElement = document.createElement("img");
    if (status === "success") {
        imgElement.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAnUlEQVR4nNWRSwoCMRBE62SCG0FEvIKIi66AAwpzs+6cTJEsdIQwTsYfMcGNDb0JVfWaCvB/o9JCuaszGw8wdlBeodKUk43dsDGEroz8uCcop0nkOYO6VZYc6XKEusmIIHMozzAGeC7LyMpFb74LQnz7yhwD3BomlydhyJ89Hi+bNyEZ8kvTso1fU2XG0HhTb06d7OvN6ZL2c2E/nhvi4bFIkTdT4AAAAABJRU5ErkJggg==";
    } else {
        imgElement.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAsElEQVR4nK2TQQ7CMAwE+6PawE+wywH+f0HiA41ZlEJQ6joCtbWUk7Pj7sbtuj0LNPQglXZfJd9pio3kbqQJrLdlX9RIR2N5gM+nUPxkRT4eUsTfvoeAVOoLH8gI1ks+Yc9bRQgRAw/JiRN6uTZDMgf5W+wgs6lv32o/xSUwRIDJzvJ1ZuXTDi20IAjEeaq3E0Kw9RkRLVIVmB8wLdJBj+4rqlUO0i6QUFxDVv9Ma+sFLPw1qs50088AAAAASUVORK5CYII=";
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
    const maxSize = 1 * 5024 * 5024; // 5MB

    if (fileInput.files.length > 0) {
        const fileSize = fileInput.files[0].size;
        if (fileSize > maxSize) {
            alert("danger" ,'Fail! ,File size exceeds 1MB. Please select a smaller file.');
            fileInput.value = ''; // Clear the selected file
        }
    }
}


