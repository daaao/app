
<#assign data = params?eval>

select module, count(*) as count
from sys_log

<#if data.module??>
 where module =:module
</#if>

group by module order by count