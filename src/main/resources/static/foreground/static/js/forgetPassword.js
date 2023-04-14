const getPasswordBtn=document.getElementById('getPassword');

getPasswordBtn.addEventListener('click', function(e) {
    Swal.fire({
      title: '請輸入您的Email',
      input: 'text',
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: '確定',
      showLoaderOnConfirm: true,
      allowOutsideClick: () => !Swal.isLoading(),
      preConfirm: (email) => {
        return fetch(`/mem/genAuthCode`, {
            method: 'POST',
            body: JSON.stringify({
              "email": `${email}`
            }),
            headers: {
              'Content-Type': 'application/json'
            }
          })
          .then(response => {
            if (!response.ok) {
              throw new Error(response.statusText)
            }
            return response.json();
          })
          .then(data => {
            Swal.fire({
              title: `${data}`,
              imageUrl: data.avatar_url
            })
          })
          .catch(error => {
            Swal.showValidationMessage(
              `Request failed: ${error}`
            )
          })
      }
    })
  }, false);