${message}<br><br>

<table style="border:0; padding-top:0px; margin-top:0px;">

  <#list additionalData?keys as key>
    <tr>
      <td><strong>${key}:</strong></td>
      <td>${additionalData[key]}</td>
    </tr>
  </#list>

</table>

<br><br>^_^
