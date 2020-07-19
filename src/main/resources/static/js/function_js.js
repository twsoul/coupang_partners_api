var newArticleDivId = "write_article_layer";

var newArticleUrl = "http://www.naver.com/";

var minimumMargine = 50;

var maximumWidth = 800;


/*

 * openLayeredDialog

 * 레이어에 떠 있는 iFrame을 웹 페이지에 표시한다.

 */

function openLayeredDialog() {

    // 배경이 되는 반투명 레이어를 생성한다.

    var div1 = document.createElement("DIV");

    div1.id = newArticleUrl;

    div1.onclick = function () {closeLayeredDialog(false);};

    div1.style.position = "absolute";

    div1.style.height = "100%";

    div1.style.width = "100%";

    div1.style.top = "0";

    div1.style.left = "0";

    div1.style.background = "#000000";

    div1.style.opacity = "0.6";

    div1.style.filter = "alpha(opacity=60)";



    // iFrame을 담는 레이어를 생성한다.

    var div2 = document.createElement("DIV");

    div2.id = newArticleDivId + "_inner";

    div2.style.position = "absolute";

    div2.style.background = "#ffffff";



    // iFrame을 생성한다.

    var iframe1 = document.createElement("IFRAME");

    iframe1.src = newArticleUrl;

    iframe1.frameborder = "0";

    iframe1.style.height = "100%";

    iframe1.style.width = "100%";



    // 앞서 생성한 Element들을 웹 페이지에 추가한다.

    div2.appendChild(iframe1);

    document.body.appendChild(div1);

    document.body.appendChild(div2);



    // 레이어의 크기를 조정한다.

    adjustmentLayeredDialog();



    // 웹브라우저의 크기를 조절할 때 발생하는 이벤트의 핸들러를 등록한다.

    window.onresize = function(event) {adjustmentLayeredDialog();};

}


/*

 * closeLayeredDialog

 * 레이어를 제거한다.

 * 인자 normalClose가 true라면 Confirm 대화 창을 표시한다.

 * 인자 normalClose가 false라면 Confirm 대화 창을 표시하지 않는다.

 */

function closeLayeredDialog(normalClose) {

    // Confirm 대화 창을 표시한다.

    if (!normalClose) {

        willClose = confirm("이 페이지에서 나가면 작성하던 내용들은 저장되지 않습니다.\n정말 나가겠습니까?");

    } else {

        willClose = true;

    }



    // 레이어를 제거한다.

    if (willClose) {

        var child_background = document.getElementById(newArticleUrl);

        var child_inner = document.getElementById(newArticleDivId + "_inner");



        if (child_background) {

            document.body.removeChild(child_background);

        }

        if (child_inner) {

            document.body.removeChild(child_inner);

        }



        // 레이어를 제거하며 웹브라우저의 크기를 조절할 때 발생하는 이벤트의 핸들러도 제거한다.

        window.onresize = null;

    }

}


/*

 * adjustmentLayeredDialog

 * 레이어를 크기와 위치를 조정한다.

 */

function adjustmentLayeredDialog() {

    var child_inner = document.getElementById(newArticleDivId + "_inner");

    if (child_inner) {

        var clientHeight = document.body.clientHeight;

        var clientWidth = document.body.clientWidth;

        var top = minimumMargine;
        var left = 0;



        var height = clientHeight - minimumMargine * 2;

        var width = clientWidth - minimumMargine * 2;



        if (width > maximumWidth) {

            width = maximumWidth;

        }



        left = (clientWidth - width) / 2;



        child_inner.style.height = height;

        child_inner.style.width = width;

        child_inner.style.top = top;

        child_inner.style.left = left;

    }

}


