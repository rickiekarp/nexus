${message}<br><br>
<table style="border:0; padding-top:0px; margin-top:0px;">
  <tr>
    <td><strong>Job:</strong></td>
    <td>${job}</td>
  </tr>
  <#if date??>
  <tr>
    <td><strong>Date:</strong></td>
    <td>${date}</td>
  </tr>
  </#if>
  <#if path??>
       <tr>
         <td><strong>Path:</strong></td>
         <td>${path}</td>
       </tr>
  </#if>
  <#if file??>
  <tr>
    <td><strong>File:</strong></td>
    <td>${file}</td>
  </tr>
  </#if>
  <#if tags??>
  <tr>
    <td><strong>Tags:</strong></td>
    <td>${tags}</td>
  </tr>
  </#if>
  <#if url??>
  <tr>
    <td><strong>URL:</strong></td>
    <td>${url}</td>
  </tr>
  </#if>
</table>
<br><br>^_^
