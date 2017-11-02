
		    <li>
			   <div class="fieldLabel"><bean:message key="{[#Project#]}.page.{[#folder#]}.{[#actionName#]}.{[#property#]}"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.{[#property#]}" value="<bean:write name="{[#actionName#]}Form" property="vo.{[#property#]}"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
