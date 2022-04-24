function activate(){
    let label1 = document.getElementById('label1');
    label1.className = "btn btn-primary active";
    let label2 = document.getElementById('label2');
    label2.className = "btn btn-primary"
}
function deactivate(){
    let label1 = document.getElementById('label1');
    label1.className = "btn btn-primary";
    let label2 = document.getElementById('label2');
    label2.className = "btn btn-primary active"
}
