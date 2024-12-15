const meses = [
    "janeiro", "fevereiro", "marco", "abril", "maio", "junho",
    "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"
];

const anos = [
    "2024", "2025", "2026" // Adicione mais anos conforme necessário
];

// Função para abrir o modal
function abrirModal(modalId) {
    document.getElementById(modalId).style.display = 'flex';
}

// Função para fechar o modal
function fecharModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

// Função para salvar uma nova categoria
function salvarCategoria() {
    const categoriaInput = document.getElementById('categoriaInput').value;
    const mesInput = document.getElementById('mesSelect').value; // Supondo que você tenha um select para o mês
    const anoInput = document.getElementById('anoSelect').value; // Supondo que você tenha um select para o ano

    if (categoriaInput) {
        const categoria = {
            nome: categoriaInput,
            mes: mesInput,
            ano: anoInput
        };

        fetch('/categoria/incluir', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(categoria)
        })
        .then(response => {
            if (response.ok) {
                document.getElementById('categoriaInput').value = '';
                fecharModal('modalNovaCategoria');
                alert("Categoria salva com sucesso!");
            } else {
                alert("Erro ao salvar categoria.");
            }
        })
        .catch(error => {
            console.error("Erro:", error);
            alert("Erro ao salvar categoria.");
        });
    } else {
        alert("Por favor, insira o nome da categoria.");
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
    const valor = document.getElementById('valorInput').value; // Captura o valor como string
    const data = document.getElementById('dataInput').value;
    const categoriaId = document.getElementById('categoriaSelect').value;
    const mesId = getUrlParameter("mes_id"); // Supondo que você esteja passando o ID do mês na URL

    // Verifica se todos os campos estão preenchidos
    if (!descricao || !valor || !data || !categoriaId || !mesId) {
        alert("Por favor, preencha todos os campos!");
        return; // Retorna se algum campo estiver vazio
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
        mes: { id: mesId }, // Inclui a referência do mês
        ano: new Date(data).getFullYear() // Captura o ano da data
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
            location.reload(); // Atualiza a página para mostrar a nova despesa
        } else {
            alert("Erro ao incluir despesa: " + response.statusText);
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
        alert("Preencha todos os campos!");
    }
}

// Função para carregar categorias do banco
function carregarCategorias() {
    fetch('/categoria/listar') // Supondo que você tenha um endpoint para listar categorias
        .then(response => response.json())
        .then(categorias => {
            const categoriaSelect = document.getElementById('categoriaSelect');
            categoriaSelect.innerHTML = ''; // Limpa o select antes de adicionar novas opções

            categorias.forEach(categoria => {
                const option = document.createElement('option');
                option.value = categoria.id;
                option.textContent = categoria.nome; // Adiciona o nome da categoria
                categoriaSelect.appendChild(option);
            });
        })
        .catch(error => console.error("Erro ao carregar categorias:", error));
}

// Chama a função para carregar categorias ao carregar a página
window.onload = function() {
    carregarCategorias();
};
