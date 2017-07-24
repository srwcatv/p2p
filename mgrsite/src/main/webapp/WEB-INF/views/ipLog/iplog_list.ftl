<#if result.result?size &gt; 0 >
		<#list result.result as data>
			<tr>
				<td>${data.username}</a></td>
				<td>${data.loginTime?string("yyyy-MM-dd HH:mm:SS")}</td>
		        <td>${data.ip}</td>
		        <td>${data.stateDisplay}</td>
		        <td>${data.loginTypeDisplay}</td>
			</tr>
		</#list>
<#else>
	<tr>
		<td colspan="7" align="center">
			<p class="text-danger">没有数据</p>
		</td>
	</tr>
	<script type="text/javascript">
		$(function(){
			$("#pagination_container").empty();
		});
	</script>
</#if>		