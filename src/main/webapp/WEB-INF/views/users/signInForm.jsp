<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp"%>

    <div class="container">
        <form action="/auth/api/signin" method="post">
            <div class="form-group">
                <label for="email">이메일</label> <!-- name : username 안하면 시큐리티로 넘어갈 때 값이 전송 안됨;; -->
                <input type="text" class="form-control" id="email" name="username" placeholder="이메일을 입력하세요" required>
            </div>
            <div class="form-group">
                <label for="password"> 패스워드 </label>
                <input type="password" class="form-control" id="password" name="password" placeholder="패스워드를 입력하세요" required>
            </div>
            <div class="form-group form-check">
                <label class="form-check-label">
                    <input class="form-check-input" type="checkbox" name="remember"> Remember me
                </label>
            </div>
            <button type="submit" class="btn btn-primary" id="btn-login">로그인</button>
        </form>

    </div>
    <script src="/js/user.js"></script>

<%@ include file="../layout/footer.jsp"%>
