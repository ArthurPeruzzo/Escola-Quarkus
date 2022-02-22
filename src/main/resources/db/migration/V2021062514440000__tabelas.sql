
create table public.tb_aluno(
    matricula bigint not null,
    nome varchar(255) null,
    email varchar(255) null,
    telefone varchar(255) null,
    constraint pk_matricula primary key (matricula)
);

create sequence seq_tipoavaliacao;

create table public.tb_tipoavaliacao(
    id bigint not null ,
    nomeavaliacao varchar(255) null,
    pesoavaliacao float null,
   constraint pk_tipo_avaliacaoid primary key(id)
);

create table public.tb_bimestre(
    id bigint not null,
    bimestre int null,
    fimBimestre timestamp null,
    inicioBimestre timestamp null,
    constraint pk_bimestreid primary key(id)
);

create sequence seq_avaliacao;

create table public.tb_avaliacao(
    id bigint not null,
    notaavaliacao float null,
    aluno_id int null,
    tipoavaliacao_id int null,
    bimestre_id int null,
    constraint fk_aluno_id foreign key (aluno_id) references public.tb_aluno (matricula),
    constraint fk_tipoavaliacao_id foreign key (tipoavaliacao_id) references public.tb_tipoAvaliacao (id),
    constraint fk_bimestre_id foreign key (bimestre_id) references public.tb_bimestre (id),
    constraint pk_avaliacaoid primary key(id)
);

create sequence seq_presenca;

create table public.tb_presenca(
    id bigint not null,
    numeroDeFaltas int null,
    id_aluno int null,
    id_bimestre int null,
    constraint fk_id_bimestre foreign key (id_bimestre) references public.tb_bimestre (id),
    constraint fk_id_aluno foreign key (id_aluno) references public.tb_aluno (matricula),
    constraint pk_presencaid primary key(id)
);

create sequence seq_alunobimestre;

create table public.tb_alunobimestre(
    alunobimestreid bigint not null,
    aluno_id int not null,
    bimestre_id int not null,
    constraint fk_aluno_id foreign key (aluno_id) references public.tb_aluno(matricula),
    constraint fk_bimestre_id foreign key (bimestre_id) references public.tb_bimestre(id),
    constraint pk_alunobimestreid primary key(alunobimestreid)
);