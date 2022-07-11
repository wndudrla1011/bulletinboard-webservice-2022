var main = {
    //각 버튼에 대한 이벤트 처리
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    //저장
    save : function () {
            //각 속성 값 포장
            var data = {
                title: $('#title').val(),
                author: $('#author').val(),
                content: $('#content').val()
            };

            //컨트롤러로 api 요청
            $.ajax({
                type: 'POST',
                url: '/api/v1/posts',
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('글이 등록되었습니다.');
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        },
    //수정
    update : function () {
            //각 속성 값 포장
            var data = {
                title: $('#title').val(),
                content: $('#content').val()
            };

            //페이지 구분을 위한 id 값 추출
            var id = $('#id').val();

            //컨트롤러로 api 요청
            $.ajax({
                type: 'PUT',
                url: '/api/v1/posts/'+id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('글이 수정되었습니다.')
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        },
    //삭제
    delete : function () {
            //페이지 구분을 위한 id 값 추출
            var id = $('#id').val();

            //컨트롤러로 api 요청
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/posts/'+id,
                dataType: 'json',
                contentType:'application/json; charset=utf-8'
            }).done(function() {
                alert('글이 삭제되었습니다.');
                window.location.href='/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
};

main.init();