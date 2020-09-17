
<script src="http://code.jquery.com/jquery-3.5.1.js"></script>
<!--로딩바-->
$(document).ready(function() {
$('#loading').hide();

$('#trans').submit(function(){
    $('#loading').show();
    return true;
});

});

<!--header_menu 호출-->
$( function() {
//header
$(".header_menu").load('/header_menu');
});

<!--footer 호출-->
$( function() {
//footer
$(".footer").load('/footer');
});