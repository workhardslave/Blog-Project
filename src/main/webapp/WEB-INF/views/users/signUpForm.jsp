<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp"%>

    <div class="container">
        <form>
            <div class="form-group">
                <label for="email">이메일</label>
                <input type="email" class="form-control" id="email" placeholder="이메일을 입력하세요" required>
            </div>
            <div class="form-group">
                <label for="password"> 패스워드 </label>
                <input type="password" class="form-control" id="password" placeholder="패스워드를 입력하세요" required>
            </div>
            <div class="form-group">
                <label for="username">이름</label>
                <input type="text" class="form-control" id="username" placeholder="이름을 입력하세요" required>
            </div>
            <button type="button" class="btn btn-primary" id="btn-save">가입완료</button>
        </form>
    </div>
    <script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>
