-- Load data for a "Credit Line Assignment Rules"

INSERT INTO public.credit_line_rules(
	id_credit_line_rule, age_range, credit_line_amount, rule_name, rule_description)
	VALUES (1, '18,25', '3000', 'CPCLAR', '$3,000 for clients aged 18 to 25 years.');
INSERT INTO public.credit_line_rules(
	id_credit_line_rule, age_range, credit_line_amount, rule_name, rule_description)
	VALUES (2, '26,30', '5000', 'CPCLAR', '$5,000 for clients aged 26 to 30 years.');
INSERT INTO public.credit_line_rules(
	id_credit_line_rule, age_range, credit_line_amount, rule_name, rule_description)
	VALUES (3, '31,65', '8000', 'CPCLAR', '$8,000 for clients aged 31 to 65 years.');
INSERT INTO public.credit_line_rules(
	id_credit_line_rule, age_range, credit_line_amount, rule_name, rule_description)
	VALUES (4, '18,65', '0', 'CPCLAR', 'Clients under 18 or over 65 are not accepted.');
	