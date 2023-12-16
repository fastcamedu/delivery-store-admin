$(function (){
    $("button[name=signup-btn]").click(function (){
        location.href = "/onboarding/step/store-owner";
    });

    $("button[name=login-btn]").click(function (){
        location.href = "/auth/login";
    });
});