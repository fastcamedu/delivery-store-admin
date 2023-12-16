$(function () {
    $('#settlementTable').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
    });

    $('.withdraw-btn').on('click', function () {
        let totalBalance = $('.total-balance').data('balance');
        if (confirm(totalBalance + '원을 출금하시겠습니까?')) {
            $.ajax({
                type: 'POST',
                url: '/api/settlement/withdraw',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify({
                    amount: totalBalance
                }),
            })
                .done(function() {
                    alert('출금이 완료되었습니다.');
                    location.reload();
                })
                .fail(function (error) {
                    alert(JSON.stringify(error));
                });
        }
    });
});