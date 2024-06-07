-- Insertion des données pour Season
INSERT INTO season (id, label) VALUES
                                   (1000, '2021-2022'),
                                   (1001, '2022-2023');

-- Insertion des données pour Team
INSERT INTO team (id, name) VALUES
                                (1000, 'Team A'),
                                (1001, 'Team B'),
                                (1002, 'Team C'),
                                (1003, 'Team D');

-- Insertion des données pour season_teams (table de liaison entre Season et Team)
INSERT INTO team_seasons (seasons_id,teams_id) VALUES
                                                  (1000, 1000),
                                                  (1000, 1001),
                                                  (1001, 1002),
                                                  (1001, 1003);

-- Insertion des données pour Day
INSERT INTO day (id, date, day_number, season_id) VALUES
                                                      (1000, '2023-01-01', 1, 1000),
                                                      (1001, '2023-01-08', 2, 1000),
                                                      (1002, '2023-01-15', 3, 1001),
                                                      (1003, '2023-01-22', 4, 1001);

-- Insertion des données pour Match
INSERT INTO matchs (id, score_team_home, score_team_away, match_date, match_status, day_id, team_home_id, team_away_id) VALUES
                                                                                                                            (1000, 2, 1, '2023-01-01 15:00:00', 'Finished', 1000, 1000, 1001),
                                                                                                                            (1001, 0, 0, '2023-01-08 15:00:00', 'Finished', 1001, 1002, 1003),
                                                                                                                            (1002, 1, 3, '2023-01-15 15:00:00', 'ongoing', 1002, 1001, 1002),
                                                                                                                            (1003, 4, 2, '2023-01-22 15:00:00', 'ongoing', 1003, 1003, 1000);

-- Insertion des données pour Comment
INSERT INTO comment (id, content, match_id) VALUES
                                                (1000, 'Great match!', 1000),
                                                (1001, 'Tough game.', 1001),
                                                (1002, 'Looking forward to this one!', 1002),
                                                (1003, 'What a comeback!', 1003);

-- Insertion des données pour Event
INSERT INTO event (id, type, player, match_id) VALUES
                                                   (1000, 'Goal', 'Player 1', 1000),
                                                   (1001, 'Yellow Card', 'Player 2', 1001),
                                                   (1002, 'Red Card', 'Player 3', 1002),
                                                   (1003, 'Assist', 'Player 4', 1003);
