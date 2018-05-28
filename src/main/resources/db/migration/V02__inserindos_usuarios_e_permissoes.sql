

INSERT INTO `usuario` (`codigo`, `nome`, `email`, `senha`) VALUES (1, 'Highlander Dantas', 'admin', '$2a$10$OlIHqx2yXYuJMmAzgC40pORZgIgYWk2SaCwDygZWg/UiF6t0XcPVy');
INSERT INTO `usuario` (`codigo`, `nome`, `email`, `senha`) VALUES (2, 'Luiz Felipe', 'luiz@teatrou.com', '$2a$10$WeUaco7vnvEtKLhBtJVtpuoE.tUMgwdr7ZXxk2/GLIkR6c7.KdpNW');


INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (1, 'ROLE_CADASTRAR_EVENTO');
INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (2, 'ROLE_EXCLUIR_EVENTO');
INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (3, 'ROLE_PESQUISAR_EVENTO');
INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (4, 'ROLE_ALTERAR_EVENTO');
INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (5, 'ROLE_CADASTRAR_USUARIO');
INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (6, 'ROLE_EXCLUIR_USUARIO');
INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (7, 'ROLE_ALTERAR_USUARIO');
INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (8, 'ROLE_REALIZAR_COMPRA');
INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (9, 'ROLE_PESQUISAR_COMPRA');
INSERT INTO `permissao` (`codigo`, `descricao`) VALUES (10, 'ROLE_PESQUISAR_USUARIO');


INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (1, 1);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (2, 1);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (1, 2);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (2, 2);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (1, 3);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (2, 3);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (1, 4);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (2, 4);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (1, 5);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (2, 5);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (1, 6);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (2, 6);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (1, 7);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (2, 7);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (1, 8);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (2, 8);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (1, 9);
INSERT INTO `usuario_permissao` (`codigo_usuario`, `codigo_permissao`) VALUES (2, 9);
