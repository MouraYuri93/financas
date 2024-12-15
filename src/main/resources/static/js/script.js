// Array com os nomes dos meses
const meses = [
    "janeiro", "fevereiro", "marco", "abril", "maio", "junho",
    "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"
];

// Obter o ano atual dinamicamente
const anoAtual = new Date().getFullYear();
document.getElementById('anoAtual').textContent = anoAtual;

// Atualizar dinamicamente os links da lista de meses
const listaMeses = document.querySelectorAll('#listaMeses li a');

listaMeses.forEach((link, index) => {
    const mes = meses[index];
    // Atualiza o atributo href
    link.href = `/mes?mes=${mes}&ano=${anoAtual}`;
    // Atualiza o texto exibido
    link.textContent = `${mes.charAt(0).toUpperCase() + mes.slice(1)} - R$ 0,00`;
});

// Função para abrir o modal
function abrirModal(modalId) {
    document.getElementById(modalId).style.display = 'flex';
}

// Função para fechar o modal
function fecharModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

// Função para salvar uma nova categoria
function adicionarCategoria() {
        const nomeCategoria = document.getElementById('nomeCategoria').value;
        const mes = `[[${mes}]]`; // Recupera o mês atual via Thymeleaf
        const ano = `[[${ano}]]`; // Recupera o ano atual via Thymeleaf

        if (nomeCategoria) {
            // Envia os dados para o servidor via POST
            fetch('/categoria/adicionar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ nome: nomeCategoria, mes: mes, ano: ano })
            })
            .then(response => {
                if (response.ok) {
                    alert('Categoria adicionada com sucesso!');
                    location.reload(); // Recarrega a página para exibir a nova categoria
                } else {
                    alert('Erro ao adicionar categoria.');
                }
            });
        } else {
            alert('Por favor, informe o nome da categoria.');
        }
    }

// Função para exibir o modal de erro
function mostrarErro() {
    abrirModal('modalErro');
}

// Função para obter o valor de um parâmetro da URL
function getUrlParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// Navegar para o mês anterior
function navegarMesAnterior() {
    const mesAtual = getUrlParameter("mes");
    const anoAtual = getUrlParameter("ano");
    const index = meses.indexOf(mesAtual);

    if (index > 0) {
        const mesAnterior = meses[index - 1];
        window.location.href = `mes?mes=${mesAnterior}&ano=${anoAtual}`;
    } else if (index === 0 && anoAtual > 2024) {
        const novoAno = anoAtual - 1;
        const mesAnterior = meses[11];
        window.location.href = `mes?mes=${mesAnterior}&ano=${novoAno}`;
    } else {
        alert("Este é o primeiro mês do ano.");
    }
}

// Navegar para o próximo mês
function navegarProximoMes() {
    const mesAtual = getUrlParameter("mes");
    const anoAtual = getUrlParameter("ano");
    const index = meses.indexOf(mesAtual);

    if (index < meses.length - 1) {
        const proximoMes = meses[index + 1];
        window.location.href = `mes?mes=${proximoMes}&ano=${anoAtual}`;
    } else if (index === 11) {
        const novoAno = parseInt(anoAtual) + 1;
        const proximoMes = meses[0];
        window.location.href = `mes?mes=${proximoMes}&ano=${novoAno}`;
    } else {
        alert("Este é o último mês do ano.");
    }
}

// Voltar do mês para a tela inicial
function voltarParaInicial() {
    window.location.href = "/";
}

// Função para formatar o valor em moeda brasileira
function formatarMoeda(input) {
    let valor = input.value;
    valor = valor.replace(/\D/g, "");
    valor = (valor / 100).toFixed(2) + "";
    valor = valor.replace(".", ",");
    valor = valor.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1.");
    input.value = "R$ " + valor;
}

// Função para incluir nova despesa
function incluirDespesa() {
    const descricao = document.getElementById('descricaoInput').value;
    const valor = document.getElementById('valorInput').value;
    const data = document.getElementById('dataInput').value;
    const categoriaId = document.getElementById('categoriaSelect').value;
    const mesId = getUrlParameter("mes_id");

    // Verifica se todos os campos estão preenchidos
    if (!descricao || !valor || !data || !categoriaId || !mesId) {
        alert("Por favor, preencha todos os campos!");
        return;
    }

    const valorNumerico = parseFloat(valor.replace("R$ ", "").replace(".", "").replace(",", "."));
    if (isNaN(valorNumerico) || valorNumerico < 0) {
        alert("Por favor, insira um valor de despesa válido.");
        return;
    }

    const despesa = {
        descricao,
        valor: valorNumerico,
        data,
        categoria: { id: categoriaId },
        mes: { id: mesId },
        ano: new Date(data).getFullYear()
    };

    fetch('/despesa/incluir', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(despesa)
    })
    .then(response => {
        if (response.ok) {
            alert("Despesa incluída com sucesso!");
            fecharModal('modalNovaDespesa');
            location.reload();
        } else {
            alert("Erro ao incluir despesa: ", "error" + response.statusText);
        }
    })
    .catch(error => console.error("Erro:", error));
}

// Função para abrir o modal de edição de despesa
function editarDespesa(id) {
    fetch(`/despesa/buscar/${id}`)
        .then(response => response.json())
        .then(despesa => {
            document.getElementById('descricaoEditar').value = despesa.descricao;
            document.getElementById('valorEditar').value = despesa.valor;
            document.getElementById('dataEditar').value = despesa.data;
            abrirModal('modalEditarDespesa');
            document.getElementById('salvarEdicao').onclick = () => salvarEdicao(id);
        });
}

// Função para salvar a edição de despesa
function salvarEdicao(id) {
    const descricao = document.getElementById('descricaoEditar').value;
    const valor = parseFloat(document.getElementById('valorEditar').value.replace("R$ ", "").replace(".", "").replace(",", "."));
    const data = document.getElementById('dataEditar').value;

    if (descricao && !isNaN(valor) && data) {
        fetch(`/despesa/editar/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ descricao, valor, data })
        })
        .then(response => response.ok ? alert("Despesa atualizada com sucesso!") : alert("Erro ao atualizar despesa."))
        .finally(() => {
            fecharModal('modalEditarDespesa');
            location.reload(); // Atualiza a página para mostrar a despesa editada
        });
    } else {
        alert("Preencha todos os campos!",);
    }
}

// Chama a função para carregar categorias ao carregar a página
window.onload = function() {
    carregarCategorias();
};

// Função para carregar categorias do banco ao selecionar mês
function carregarCategorias() {
    const urlParams = new URLSearchParams(window.location.search);
    const mesSelecionado = urlParams.get('mes'); // Obtém o mês da URL
    const anoSelecionado = urlParams.get('ano'); // Obtém o ano da URL

    fetch(`/categoria/listar?mes=${mesSelecionado}&ano=${anoSelecionado}`)
        .then(response => {
            if (!response.ok) throw new Error("Erro ao buscar categorias.");
            return response.json();
        })
        .then(categorias => {
            const categoriaLista = document.getElementById('categoriaLista');
            categoriaLista.innerHTML = ''; // Limpa a lista anterior

            if (categorias.length === 0) {
                categoriaLista.innerHTML = '<li>Nenhuma categoria encontrada para este mês.</li>';
            } else {
                categorias.forEach(categoria => {
                    const li = document.createElement('li');
                    const link = document.createElement('a');
                    link.href = `/categoria?categoria=${categoria.nome}&mes=${mesSelecionado}`;
                    link.textContent = `${categoria.nome} - R$ 0,00`;
                    li.appendChild(link);
                    categoriaLista.appendChild(li);
                });
            }
        })
        .catch(error => console.error("Erro ao carregar categorias:", error));
}

// Deletar uma categoria
function deletarCategoria(id) {
    if (confirm("Tem certeza que deseja deletar esta categoria?")) {
        fetch(`/categoria/deletar/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    alert("Categoria deletada com sucesso!");
                    carregarCategorias(); // Recarrega a lista
                } else {
                    alert("Erro ao deletar a categoria.");
                }
            })
            .catch(error => console.error("Erro ao deletar categoria:", error));
    }
}

// Editar uma categoria
function editarCategoria(button, id, nomeAtual) {
    const novoNome = prompt("Digite o novo nome da categoria:", nomeAtual);
    if (novoNome && novoNome.trim() !== "") {
        fetch('/categoria/atualizar', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id: id, nome: novoNome })
        })
        .then(response => {
            if (response.ok) {
                alert("Categoria atualizada com sucesso!");
                carregarCategorias(); // Recarrega a lista
            } else {
                alert("Erro ao atualizar a categoria.");
            }
        })
        .catch(error => console.error("Erro ao atualizar categoria:", error));
    }
}

// Função para fechar o modal
    function fecharModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }

// Função para abrir o modal e definir o ano atual no campo de ano
    function abrirModalNovaCategoria() {
        const anoAtual = new Date().getFullYear(); // Obtém o ano atual
        document.getElementById('anoInput').value = anoAtual; // Preenche o campo de ano
        document.getElementById('modalNovaCategoria').style.display = 'block'; // Abre o modal
    }

    // Função para fechar o modal
    function fecharModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }

// Função para salvar a nova categoria
function salvarCategoria() {
    const categoriaNome = document.getElementById('categoriaInput').value;
    const mesSelecionado = document.getElementById('mesSelect').value;
    const anoSelecionado = document.getElementById('anoInput').value;

    // Validação
    if (!categoriaNome || mesSelecionado === "" || mesSelecionado === "Selecione") {
        alert("Por favor, preencha todos os campos corretamente.",);
        return;
    }

    // Envio dos dados via Fetch API
    fetch('/categoria/adicionar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nome: categoriaNome,
            mes: mesSelecionado,
            ano: anoSelecionado
        })
    })
    .then(response => {
        if (response.ok) {
            alert("Categoria adicionada com sucesso!");
            fecharModal('modalNovaCategoria');
            location.reload(); // Recarrega a página para atualizar os dados
        } else {
            alert("Erro ao adicionar a categoria.",);
        }
    })
    .catch(error => {
        console.error("Erro ao enviar a requisição:", error);
        alert("Ocorreu um erro ao salvar a categoria.");
    });
}

//Alert Toast
function showAlertaToast(message, type = "success") {
    const AlertaToast = document.createElement("div");
    AlertaToast.className = `AlertaToast AlertaToast-${type}`;

    let icon = "";
    switch (type) {
        case "success":
            icon = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="#28a745" class="bi bi-check-circle-fill" viewBox="0 0 16 16">'
                 + '<path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM6.97 10.03a.75.75 0 0 0 1.08 0L11.03 7a.75.75 0 1 0-1.08-1.06L7.5 8.44l-1.47-1.47a.75.75 0 0 0-1.08 1.06l2 2z"/>'
                 + '</svg>';
            break;
        case "error":
            icon = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">'
                 + '<circle cx="12" cy="12" r="12" fill="#dc3545"/>'
                 + '<line x1="8" y1="8" x2="16" y2="16" stroke="white" stroke-width="2" stroke-linecap="round"/>'
                 + '<line x1="8" y1="16" x2="16" y2="8" stroke="white" stroke-width="2" stroke-linecap="round"/>'
                 + '</svg>';
            break;
        case "warning":
            icon = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="#ffc107" class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">'
                 + '<path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5m.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>'
                 + '</svg>';
            break;
    }

    AlertaToast.innerHTML = `${icon} ${message}`;

    document.body.appendChild(AlertaToast);

    setTimeout(() => {
        AlertaToast.classList.add("show");
    }, 100);

    setTimeout(() => {
        AlertaToast.classList.remove("show");
        setTimeout(() => AlertaToast.remove(), 300);
    }, 3000);
}