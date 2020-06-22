var sseConnection = function() {
  var eventSource = new EventSource('/new_connection');
  eventSource.addEventListener('workflowStatusChange',function(e){
        var data=JSON.parse(e.data);
        console.log(data);
        var el=document.querySelector('#'+data.name+'>.status');
        var text=data.status;
        setStatus(el,text);
       }, false );

  eventSource.addEventListener('workflowCreated',function(e){
        location.reload();
   }, false );
  eventSource.onmessage = function(e) {
         location.reload();
<!--            console.log(e.data)-->
   };
}

function setStatus(el,text){
    el.className="status "+text;
    el.innerHTML=text;
}

window.onload = sseConnection;

window.onbeforeunload = function() {
  eventSource.close();
 }