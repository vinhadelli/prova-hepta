var editar = new Vue({
	el:"#editar",
    data: {
		setor: [],
		funcionario: [],
		dnome: '',
		demail: '',
		dsalario: '',
		didade: '',
		dsetor: '',
    },
    created: function(){
		let vm =  this;
		vm.getSetores();
		vm.getFuncionario();
    },
    methods:{
		getSetores: function () {
            axios.get('/funcionarios/rs/funcionarios/setor')
            .then(response => {this.setor = response.data;
            }).catch(function(error) {
                alert('Ocorreu um erro ao resgatar os setores: ' + error);
            });
		},

		getFuncionario: function(){
			let id = localStorage.getItem("funcionario");
			axios.get('/funcionarios/rs/funcionarios/'+id)
            .then(response => {this.funcionario = response.data;
            }).catch(function(error) {
                alert('Ocorreu um erro ao resgatar o funcionário: ' + error);
			});

			funcionario.nome = id;
			this.dnome = funcionario.nome;
			this.demail = funcionario.email;
			this.dsalario = funcionario.salario;
			this.didade = funcionario.idade;
			this.dsetor = funcionario.setor.id;
		},
		
		editarFuncionario: function() {

			let select = document.querySelector("select [id=dropSetor]")

			let id = select.selectedIndex;
			let nsetor = select.options[id].text;

			let func = {
						id: funcionario.id,
						nome: dnome,
						email: demail,
						salario: dsalario,
						idade: didade,
						setor: {id: id,
								nome: nsetor}
						};
			
			axios.put('/funcionarios/rs/funcionarios'+id, func)
			.then(response => {
				alert("Funcionario editado com sucesso")
				window.location.href = '/funcionarios/';},
			error => {alert('Ocorreu um erro.')});

		},	
    }
});