const meses = [
    "janeiro", "fevereiro", "marco", "abril", "maio", "junho",
    "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"
];

const categorias = [
    "cartao_credito", "educacao", "emprestimo",
    "entretenimento", "moradia", "saude"
];

// Função para obter o valor de um parâmetro da URL
function getUrlParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// Navegar para o mês anterior
function navegarMesAnterior() {
    const mesAtual = getUrlParameter("mes");
    const index = meses.indexOf(mesAtual);

    if (index > 0) {
        const mesAnterior = meses[index - 1];
        window.location.href = `mes?mes=${mesAnterior}`;
    } else {
        alert("Este é o primeiro mês do ano.");
    }
}

// Navegar para o próximo mês
function navegarProximoMes() {
    const mesAtual = getUrlParameter("mes");
    const index = meses.indexOf(mesAtual);

    if (index < meses.length - 1) {
        const proximoMes = meses[index + 1];
        window.location.href = `mes?mes=${proximoMes}`;
    } else {
        alert("Este é o último mês do ano.");
    }
}

// Navegar para a categoria anterior
function navegarCategoriaAnterior() {
    const categoriaAtual = getUrlParameter("categoria");
    const mesAtual = getUrlParameter("mes");
    const index = categorias.indexOf(categoriaAtual);

    if (index > 0) {
        const categoriaAnterior = categorias[index - 1];
        window.location.href = `categoria?categoria=${categoriaAnterior}&mes=${mesAtual}`;
    } else {
        alert("Esta é a primeira categoria.");
    }
}

// Navegar para a próxima categoria
function navegarCategoriaProxima() {
    const categoriaAtual = getUrlParameter("categoria");
    const mesAtual = getUrlParameter("mes");
    const index = categorias.indexOf(categoriaAtual);

    if (index < categorias.length - 1) {
        const proximaCategoria = categorias[index + 1];
        window.location.href = `categoria?categoria=${proximaCategoria}&mes=${mesAtual}`;
    } else {
        alert("Esta é a última categoria.");
    }
}

// Voltar da categoria para o mês correspondente
function voltarParaMes() {
    const mes = getUrlParameter("mes");
    if (mes) {
        window.location.href = `/mes?mes=${mes}`;
    } else {
        window.location.href = "/"; // Se não houver parâmetro de mês, volta para a página inicial
    }
}

// Voltar do mês para a tela inicial
function voltarParaInicial() {
    window.location.href = "/";
}
