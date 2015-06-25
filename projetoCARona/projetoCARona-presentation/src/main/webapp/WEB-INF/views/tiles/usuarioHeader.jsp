<!-- *****************************************************************************************************************
	 HEADER
	 ***************************************************************************************************************** -->

<!-- fixed navbar top -->
	<div class="navbar navbar-lg navbar-inverse navbar-static-top" role="navigation">
    	<div class="container">
		<div class="col-md-12">
        	<div class="navbar-header">
          		<a href="homeUsuario.html"><h4 class="demo-section-title logoCustom">CARona</h4></a> 
        	</div>
        	
        	<div class="navbar-collapse collapse">          
          		<ul class="nav navbar-nav navbar-right">            
            		<li><a href="homeUsuario.html"><span class="glyphicon glyphicon-home"></span></a></li>
            		<!-- <li><a href="configuracoes.html"><span class="glyphicon glyphicon-cog"></span></a></li>  -->
            		
            		<li class="dropdown">
	                	<a href="pesquisaSolicitacoes.html" class="dropdown-toggle" data-toggle="dropdown">
	                  		<span class="glyphicon glyphicon-bell"></span>&nbsp;
	                  		<span class="badge">3</span>	
	                  	</a>
	                  	
	                  	
	                  	<ul class="dropdown-menu">
	                  		<!-- 
	                  		<c:if test="${totalSolicitacoes > 0}">
    							<li>Sem solicitacoes</li>
    						</c:if>
    						 -->
	                  		<c:forEach items="${listaSolicitacoes}" var="solicitacao">
	                  			<li><a>
				  					<div class="row">
				  						<div class="col-md-12">
				  							<span class="glyphicon glyphicon-map-marker"></span> ${solicitacao.idUsuario} solicitou vaga na sua carona			  						
					  					</div>
					  				</div>
		    	                </a></li>
	                    		<li class="divider"></li>
		                	</c:forEach>
	                  		
	                  		<li><a>
			  					<div class="row">
			  						<div class="col-md-12">
			  							<span class="glyphicon glyphicon-map-marker"></span> Fulano solicitou carona de X pra Y			  						
				  					</div>
				  				</div>
	    	                </a></li>
	                    	<li class="divider"></li>
	        	            <li><a>
			  					<div class="row">
			  						<div class="col-md-12">
			  							<span class="glyphicon glyphicon-user"></span> Fulano solicitou sua amizade			  						
				  					</div>
				  				</div>
	    	                </a></li>
	                    	<li class="divider"></li>
	        	            <li><a>
			  					<div class="row">
			  						<div class="col-md-12">
			  							<span class="glyphicon glyphicon-info-sign"></span> Nova carona cadastrada de origem para destino			  						
			  						</div>
				  				</div>
		                    </a></li>
		                         
	    	        	</ul>
	    	        	
	        	    </li>
            		<li class="dropdown">
	                	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
	                 		<div class="col-md-5">
	                 			<div class="profile-userpic">
									<img src="../images/user1.png" class="img-responsive" alt="">
								</div>
	                 		</div>
	                 		&nbsp;${nomeUsuario}
	                  	</a>
	                    
	                  
	                  <ul class="dropdown-menu">
	                    <li><a href="perfil.html">
	                    	<span class="glyphicon glyphicon-user"></span>&nbsp;Perfil
	                    </a></li>
	                    <li class="divider"></li>
	                    <li><a href="home.html">
	                    	<span class="glyphicon glyphicon-user"></span>&nbsp;Sair
	                    </a></li>
	                  </ul>
	                </li>
	             </ul>            		
        	</div><!--/.nav-collapse -->
        	</div>
      	</div>
    </div>
