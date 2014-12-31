/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.analysis;

import org.openflexo.foundation.fml.parser.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object o);
    Object getOut(Node node);
    void setOut(Node node, Object o);

    void caseStart(Start node);
    void caseACompilationUnit(ACompilationUnit node);
    void caseANamespaceDeclaration(ANamespaceDeclaration node);
    void caseAUseDeclaration(AUseDeclaration node);
    void caseAImportDeclaration(AImportDeclaration node);
    void caseAViewPointMainDeclaration(AViewPointMainDeclaration node);
    void caseAVirtualModelMainDeclaration(AVirtualModelMainDeclaration node);
    void caseAFlexoConceptMainDeclaration(AFlexoConceptMainDeclaration node);
    void caseAViewpointDeclaration(AViewpointDeclaration node);
    void caseAVirtualModelDeclaration(AVirtualModelDeclaration node);
    void caseAFlexoConceptDeclaration(AFlexoConceptDeclaration node);
    void caseAModelSlotMemberVirtualModelBodyDeclaration(AModelSlotMemberVirtualModelBodyDeclaration node);
    void caseAFlexoConceptVirtualModelBodyDeclaration(AFlexoConceptVirtualModelBodyDeclaration node);
    void caseAFlexoConceptMemberVirtualModelBodyDeclaration(AFlexoConceptMemberVirtualModelBodyDeclaration node);
    void caseAFlexoConceptMemberFlexoConceptBodyDeclaration(AFlexoConceptMemberFlexoConceptBodyDeclaration node);
    void caseAFlexoRoleFlexoConceptMemberDeclaration(AFlexoRoleFlexoConceptMemberDeclaration node);
    void caseAFlexoBehaviourFlexoConceptMemberDeclaration(AFlexoBehaviourFlexoConceptMemberDeclaration node);
    void caseAEmptyFlexoConceptMemberDeclaration(AEmptyFlexoConceptMemberDeclaration node);
    void caseAModelSlotDeclaration(AModelSlotDeclaration node);
    void caseAFlexoRoleDeclaration(AFlexoRoleDeclaration node);
    void caseAFlexoBehaviourDeclaration(AFlexoBehaviourDeclaration node);
    void caseABlockFlexoBehaviourBody(ABlockFlexoBehaviourBody node);
    void caseAEmptyFlexoBehaviourBody(AEmptyFlexoBehaviourBody node);
    void caseAOneFmlValuePairs(AOneFmlValuePairs node);
    void caseAManyFmlValuePairs(AManyFmlValuePairs node);
    void caseAFmlValuePair(AFmlValuePair node);
    void caseAConditionalFmlValue(AConditionalFmlValue node);
    void caseAIdentifierFmlValue(AIdentifierFmlValue node);
    void caseAOneFormalArgumentsList(AOneFormalArgumentsList node);
    void caseAManyFormalArgumentsList(AManyFormalArgumentsList node);
    void caseAPrimitiveFormalArgument(APrimitiveFormalArgument node);
    void caseAReferenceFormalArgument(AReferenceFormalArgument node);
    void caseATechnologySpecificFormalArgument(ATechnologySpecificFormalArgument node);
    void caseANormalAnnotationAnnotation(ANormalAnnotationAnnotation node);
    void caseAMarkerAnnotationAnnotation(AMarkerAnnotationAnnotation node);
    void caseASingleElementAnnotationAnnotation(ASingleElementAnnotationAnnotation node);
    void caseANormalAnnotation(ANormalAnnotation node);
    void caseAMarkerAnnotation(AMarkerAnnotation node);
    void caseASingleElementAnnotation(ASingleElementAnnotation node);
    void caseAOneElementValuePairs(AOneElementValuePairs node);
    void caseAManyElementValuePairs(AManyElementValuePairs node);
    void caseAConditionalElementValue(AConditionalElementValue node);
    void caseAIdentifierElementValue(AIdentifierElementValue node);
    void caseAAnnotationElementValue(AAnnotationElementValue node);
    void caseAArrayInitializerElementValue(AArrayInitializerElementValue node);
    void caseAGtTypeParameters(AGtTypeParameters node);
    void caseAShrTypeParameters(AShrTypeParameters node);
    void caseAUshrTypeParameters(AUshrTypeParameters node);
    void caseASuper(ASuper node);
    void caseAOneSuperTypeList(AOneSuperTypeList node);
    void caseAManySuperTypeList(AManySuperTypeList node);
    void caseAElementValuePair(AElementValuePair node);
    void caseASimpleConditionalExpression(ASimpleConditionalExpression node);
    void caseAQmarkOrExOrConditionalExpression(AQmarkOrExOrConditionalExpression node);
    void caseAQmarkOrExIdConditionalExpression(AQmarkOrExIdConditionalExpression node);
    void caseAQmarkOrIdOrConditionalExpression(AQmarkOrIdOrConditionalExpression node);
    void caseAQmakrOrIdIdConditionalExpression(AQmakrOrIdIdConditionalExpression node);
    void caseAQmakrIdExOrConditionalExpression(AQmakrIdExOrConditionalExpression node);
    void caseAQmakrIdExIdConditionalExpression(AQmakrIdExIdConditionalExpression node);
    void caseAQmakrIdIdOrConditionalExpression(AQmakrIdIdOrConditionalExpression node);
    void caseAQmakrIdIdIdConditionalExpression(AQmakrIdIdIdConditionalExpression node);
    void caseAElementValueArrayInitializer(AElementValueArrayInitializer node);
    void caseAOneTypeParameterList(AOneTypeParameterList node);
    void caseAManyTypeParameterList(AManyTypeParameterList node);
    void caseAOneTypeParameterListShr(AOneTypeParameterListShr node);
    void caseAManyTypeParameterListShr(AManyTypeParameterListShr node);
    void caseAOneTypeParameterListUshr(AOneTypeParameterListUshr node);
    void caseAManyTypeParameterListUshr(AManyTypeParameterListUshr node);
    void caseAOneInterfaceTypeList(AOneInterfaceTypeList node);
    void caseAManyInterfaceTypeList(AManyInterfaceTypeList node);
    void caseASimpleConditionalOrExpression(ASimpleConditionalOrExpression node);
    void caseABarBarOrOrConditionalOrExpression(ABarBarOrOrConditionalOrExpression node);
    void caseABarBarOrIdConditionalOrExpression(ABarBarOrIdConditionalOrExpression node);
    void caseABarBarIdOrConditionalOrExpression(ABarBarIdOrConditionalOrExpression node);
    void caseABarBarIdIdConditionalOrExpression(ABarBarIdIdConditionalOrExpression node);
    void caseAExpression(AExpression node);
    void caseAOneElementValues(AOneElementValues node);
    void caseAManyElementValues(AManyElementValues node);
    void caseATypeParameter(ATypeParameter node);
    void caseATypeParameterShr(ATypeParameterShr node);
    void caseATypeParameterUshr(ATypeParameterUshr node);
    void caseAGtTypeArguments(AGtTypeArguments node);
    void caseAShrTypeArguments(AShrTypeArguments node);
    void caseAUshrTypeArguments(AUshrTypeArguments node);
    void caseATypeArgumentsShrNoGt(ATypeArgumentsShrNoGt node);
    void caseATypeArgumentsUshrNoGtGt(ATypeArgumentsUshrNoGtGt node);
    void caseATypeArgumentsUshrNoGt(ATypeArgumentsUshrNoGt node);
    void caseAInstanceInitializer(AInstanceInitializer node);
    void caseAStaticInitializer(AStaticInitializer node);
    void caseAPrimitiveConstantDeclaration(APrimitiveConstantDeclaration node);
    void caseAReferenceConstantDeclaration(AReferenceConstantDeclaration node);
    void caseADefaultValue(ADefaultValue node);
    void caseASimpleConditionalAndExpression(ASimpleConditionalAndExpression node);
    void caseAAmpAmpAnOrConditionalAndExpression(AAmpAmpAnOrConditionalAndExpression node);
    void caseAAmpAmpAnIdConditionalAndExpression(AAmpAmpAnIdConditionalAndExpression node);
    void caseAAmpAmpIdOrConditionalAndExpression(AAmpAmpIdOrConditionalAndExpression node);
    void caseAAmpAmpIdIdConditionalAndExpression(AAmpAmpIdIdConditionalAndExpression node);
    void caseAConditionalAssignmentExpression(AConditionalAssignmentExpression node);
    void caseAAssignmentAssignmentExpression(AAssignmentAssignmentExpression node);
    void caseATypeBound(ATypeBound node);
    void caseAArgumentTypeBoundShr(AArgumentTypeBoundShr node);
    void caseABoundTypeBoundShr(ABoundTypeBoundShr node);
    void caseAArgumentTypeBoundUshr(AArgumentTypeBoundUshr node);
    void caseABoundTypeBoundUshr(ABoundTypeBoundUshr node);
    void caseATypeComponent(ATypeComponent node);
    void caseAOneActualTypeArgumentList(AOneActualTypeArgumentList node);
    void caseAManyActualTypeArgumentList(AManyActualTypeArgumentList node);
    void caseAOneActualTypeArgumentListShr(AOneActualTypeArgumentListShr node);
    void caseAManyActualTypeArgumentListShr(AManyActualTypeArgumentListShr node);
    void caseAOneActualTypeArgumentListUshr(AOneActualTypeArgumentListUshr node);
    void caseAManyActualTypeArgumentListUshr(AManyActualTypeArgumentListUshr node);
    void caseAOneActualTypeArgumentListUshrNoGt(AOneActualTypeArgumentListUshrNoGt node);
    void caseAManyActualTypeArgumentListUshrNoGt(AManyActualTypeArgumentListUshrNoGt node);
    void caseABlock(ABlock node);
    void caseAThrows(AThrows node);
    void caseAArguments(AArguments node);
    void caseAOneVariableDeclarators(AOneVariableDeclarators node);
    void caseAManyVariableDeclarators(AManyVariableDeclarators node);
    void caseATechnologySpecificType(ATechnologySpecificType node);
    void caseANumericPrimitiveType(ANumericPrimitiveType node);
    void caseABooleanPrimitiveType(ABooleanPrimitiveType node);
    void caseASimpleInclusiveOrExpression(ASimpleInclusiveOrExpression node);
    void caseABarOrOrInclusiveOrExpression(ABarOrOrInclusiveOrExpression node);
    void caseABarOrIdInclusiveOrExpression(ABarOrIdInclusiveOrExpression node);
    void caseABarIdOrInclusiveOrExpression(ABarIdOrInclusiveOrExpression node);
    void caseABarIdIdInclusiveOrExpression(ABarIdIdInclusiveOrExpression node);
    void caseAExpressionAssignment(AExpressionAssignment node);
    void caseAIdentifierAssignment(AIdentifierAssignment node);
    void caseAAdditionalBound(AAdditionalBound node);
    void caseAAdditionalBoundShrNoGt(AAdditionalBoundShrNoGt node);
    void caseAAdditionalBoundUshrNoGtGt(AAdditionalBoundUshrNoGtGt node);
    void caseAPrimitiveActualTypeArgument(APrimitiveActualTypeArgument node);
    void caseAReferenceActualTypeArgument(AReferenceActualTypeArgument node);
    void caseAWildcardActualTypeArgument(AWildcardActualTypeArgument node);
    void caseAReferenceActualTypeArgumentShr(AReferenceActualTypeArgumentShr node);
    void caseAWildcardActualTypeArgumentShr(AWildcardActualTypeArgumentShr node);
    void caseAReferenceActualTypeArgumentUshr(AReferenceActualTypeArgumentUshr node);
    void caseAWildcardActualTypeArgumentUshr(AWildcardActualTypeArgumentUshr node);
    void caseAReferenceActualTypeArgumentUshrNoGt(AReferenceActualTypeArgumentUshrNoGt node);
    void caseAWildcardActualTypeArgumentUshrNoGt(AWildcardActualTypeArgumentUshrNoGt node);
    void caseAVariableDeclarationBlockStatement(AVariableDeclarationBlockStatement node);
    void caseAStatementBlockStatement(AStatementBlockStatement node);
    void caseAOneFormalParameterList(AOneFormalParameterList node);
    void caseAManyFormalParameterList(AManyFormalParameterList node);
    void caseAOneExceptionTypeList(AOneExceptionTypeList node);
    void caseAManyExceptionTypeList(AManyExceptionTypeList node);
    void caseAOneExArgumentList(AOneExArgumentList node);
    void caseAOneIdArgumentList(AOneIdArgumentList node);
    void caseAManyExArgumentList(AManyExArgumentList node);
    void caseAManyIdArgumentList(AManyIdArgumentList node);
    void caseASimpleVariableDeclarator(ASimpleVariableDeclarator node);
    void caseAInitializerVariableDeclarator(AInitializerVariableDeclarator node);
    void caseAIntegralNumericType(AIntegralNumericType node);
    void caseAFloatingNumericType(AFloatingNumericType node);
    void caseASimpleExclusiveOrExpression(ASimpleExclusiveOrExpression node);
    void caseACaretOrAnExclusiveOrExpression(ACaretOrAnExclusiveOrExpression node);
    void caseACaretOrIdExclusiveOrExpression(ACaretOrIdExclusiveOrExpression node);
    void caseACaretIdAnExclusiveOrExpression(ACaretIdAnExclusiveOrExpression node);
    void caseACaretIdIdExclusiveOrExpression(ACaretIdIdExclusiveOrExpression node);
    void caseAIdentifierLeftHandSide(AIdentifierLeftHandSide node);
    void caseAFieldLeftHandSide(AFieldLeftHandSide node);
    void caseAArrayLeftHandSide(AArrayLeftHandSide node);
    void caseAAssignAssignmentOperator(AAssignAssignmentOperator node);
    void caseAStarAssignAssignmentOperator(AStarAssignAssignmentOperator node);
    void caseASlashAssignAssignmentOperator(ASlashAssignAssignmentOperator node);
    void caseAPercentAssignAssignmentOperator(APercentAssignAssignmentOperator node);
    void caseAPlusAssignAssignmentOperator(APlusAssignAssignmentOperator node);
    void caseAMinusAssignAssignmentOperator(AMinusAssignAssignmentOperator node);
    void caseAShlAssignAssignmentOperator(AShlAssignAssignmentOperator node);
    void caseAShrAssignAssignmentOperator(AShrAssignAssignmentOperator node);
    void caseAUshrAssignAssignmentOperator(AUshrAssignAssignmentOperator node);
    void caseAAmpAssignAssignmentOperator(AAmpAssignAssignmentOperator node);
    void caseACaretAssignAssignmentOperator(ACaretAssignAssignmentOperator node);
    void caseABarAssignAssignmentOperator(ABarAssignAssignmentOperator node);
    void caseAWildcard(AWildcard node);
    void caseAWildcardShr(AWildcardShr node);
    void caseAWildcardUshr(AWildcardUshr node);
    void caseAWildcardUshrNoGt(AWildcardUshrNoGt node);
    void caseALocalVariableDeclarationStatement(ALocalVariableDeclarationStatement node);
    void caseANoTrailStatement(ANoTrailStatement node);
    void caseALabelStatement(ALabelStatement node);
    void caseAIfStatement(AIfStatement node);
    void caseAIfElseStatement(AIfElseStatement node);
    void caseAWhileLoopStatement(AWhileLoopStatement node);
    void caseAForLoopStatement(AForLoopStatement node);
    void caseAPrimitiveVarArgLastFormalParameter(APrimitiveVarArgLastFormalParameter node);
    void caseAReferenceVarArgLastFormalParameter(AReferenceVarArgLastFormalParameter node);
    void caseASimpleLastFormalParameter(ASimpleLastFormalParameter node);
    void caseAOneFormalParameters(AOneFormalParameters node);
    void caseAManyFormalParameters(AManyFormalParameters node);
    void caseAExceptionType(AExceptionType node);
    void caseAGtNonWildTypeArguments(AGtNonWildTypeArguments node);
    void caseAShrNonWildTypeArguments(AShrNonWildTypeArguments node);
    void caseAUshrNonWildTypeArguments(AUshrNonWildTypeArguments node);
    void caseANoArrayPrimary(ANoArrayPrimary node);
    void caseAArrayPrimary(AArrayPrimary node);
    void caseAExpressionVariableInitializer(AExpressionVariableInitializer node);
    void caseAIdentifierVariableInitializer(AIdentifierVariableInitializer node);
    void caseAArrayVariableInitializer(AArrayVariableInitializer node);
    void caseAByteIntegralType(AByteIntegralType node);
    void caseAShortIntegralType(AShortIntegralType node);
    void caseAIntIntegralType(AIntIntegralType node);
    void caseALongIntegralType(ALongIntegralType node);
    void caseACharIntegralType(ACharIntegralType node);
    void caseAFloatFloatingPointType(AFloatFloatingPointType node);
    void caseADoubleFloatingPointType(ADoubleFloatingPointType node);
    void caseASimpleAndExpression(ASimpleAndExpression node);
    void caseAAmpAnEqAndExpression(AAmpAnEqAndExpression node);
    void caseAAmpAnIdAndExpression(AAmpAnIdAndExpression node);
    void caseAAmpIdEqAndExpression(AAmpIdEqAndExpression node);
    void caseAAmpIdIdAndExpression(AAmpIdIdAndExpression node);
    void caseAPrimaryFieldAccess(APrimaryFieldAccess node);
    void caseASuperFieldAccess(ASuperFieldAccess node);
    void caseAReferenceSuperFieldAccess(AReferenceSuperFieldAccess node);
    void caseAPrimaryExArrayAccess(APrimaryExArrayAccess node);
    void caseAPrimaryIdArrayAccess(APrimaryIdArrayAccess node);
    void caseAIdentifierExArrayAccess(AIdentifierExArrayAccess node);
    void caseAIdentifierIdArrayAccess(AIdentifierIdArrayAccess node);
    void caseAExtendsPrimitiveWildcardBounds(AExtendsPrimitiveWildcardBounds node);
    void caseAExtendsReferenceWildcardBounds(AExtendsReferenceWildcardBounds node);
    void caseASuperPrimitiveWildcardBounds(ASuperPrimitiveWildcardBounds node);
    void caseASuperReferenceWildcardBounds(ASuperReferenceWildcardBounds node);
    void caseAExtendsReferenceWildcardBoundsShr(AExtendsReferenceWildcardBoundsShr node);
    void caseASuperReferenceWildcardBoundsShr(ASuperReferenceWildcardBoundsShr node);
    void caseAExtendsReferenceWildcardBoundsUshr(AExtendsReferenceWildcardBoundsUshr node);
    void caseASuperReferenceWildcardBoundsUshr(ASuperReferenceWildcardBoundsUshr node);
    void caseAExtendsReferenceWildcardBoundsUshrNoGt(AExtendsReferenceWildcardBoundsUshrNoGt node);
    void caseASuperReferenceWildcardBoundsUshrNoGt(ASuperReferenceWildcardBoundsUshrNoGt node);
    void caseAPrimitiveLocalVariableDeclaration(APrimitiveLocalVariableDeclaration node);
    void caseAReferenceLocalVariableDeclaration(AReferenceLocalVariableDeclaration node);
    void caseABlockStatementWithoutTrailingSubstatement(ABlockStatementWithoutTrailingSubstatement node);
    void caseAEmptyStatementStatementWithoutTrailingSubstatement(AEmptyStatementStatementWithoutTrailingSubstatement node);
    void caseAExpressionStatementStatementWithoutTrailingSubstatement(AExpressionStatementStatementWithoutTrailingSubstatement node);
    void caseAAssertStatementStatementWithoutTrailingSubstatement(AAssertStatementStatementWithoutTrailingSubstatement node);
    void caseASwitchStatementStatementWithoutTrailingSubstatement(ASwitchStatementStatementWithoutTrailingSubstatement node);
    void caseADoStatementStatementWithoutTrailingSubstatement(ADoStatementStatementWithoutTrailingSubstatement node);
    void caseABreakStatementStatementWithoutTrailingSubstatement(ABreakStatementStatementWithoutTrailingSubstatement node);
    void caseAContinueStatementStatementWithoutTrailingSubstatement(AContinueStatementStatementWithoutTrailingSubstatement node);
    void caseAReturnStatementStatementWithoutTrailingSubstatement(AReturnStatementStatementWithoutTrailingSubstatement node);
    void caseASynchronizedStatementStatementWithoutTrailingSubstatement(ASynchronizedStatementStatementWithoutTrailingSubstatement node);
    void caseAThrowStatementStatementWithoutTrailingSubstatement(AThrowStatementStatementWithoutTrailingSubstatement node);
    void caseATryStatementStatementWithoutTrailingSubstatement(ATryStatementStatementWithoutTrailingSubstatement node);
    void caseALabeledStatement(ALabeledStatement node);
    void caseAExpressionIfThenStatement(AExpressionIfThenStatement node);
    void caseAIdentifierIfThenStatement(AIdentifierIfThenStatement node);
    void caseAExpressionIfThenElseStatement(AExpressionIfThenElseStatement node);
    void caseAIdentifierIfThenElseStatement(AIdentifierIfThenElseStatement node);
    void caseAExpressionWhileStatement(AExpressionWhileStatement node);
    void caseAIdentifierWhileStatement(AIdentifierWhileStatement node);
    void caseABasicForForStatement(ABasicForForStatement node);
    void caseAEnhancedForForStatement(AEnhancedForForStatement node);
    void caseAPrimitiveFormalParameter(APrimitiveFormalParameter node);
    void caseAReferenceFormalParameter(AReferenceFormalParameter node);
    void caseAOnePrimitiveReferenceTypeList(AOnePrimitiveReferenceTypeList node);
    void caseAOneReferenceReferenceTypeList(AOneReferenceReferenceTypeList node);
    void caseAManyPrimitiveReferenceTypeList(AManyPrimitiveReferenceTypeList node);
    void caseAManyReferenceReferenceTypeList(AManyReferenceReferenceTypeList node);
    void caseAOneReferenceReferenceTypeListShr(AOneReferenceReferenceTypeListShr node);
    void caseAManyReferenceReferenceTypeListShr(AManyReferenceReferenceTypeListShr node);
    void caseAOneReferenceReferenceTypeListUshr(AOneReferenceReferenceTypeListUshr node);
    void caseAManyReferenceReferenceTypeListUshr(AManyReferenceReferenceTypeListUshr node);
    void caseALiteralPrimaryNoNewArray(ALiteralPrimaryNoNewArray node);
    void caseAPrimitivePrimaryNoNewArray(APrimitivePrimaryNoNewArray node);
    void caseAReferencePrimaryNoNewArray(AReferencePrimaryNoNewArray node);
    void caseAVoidPrimaryNoNewArray(AVoidPrimaryNoNewArray node);
    void caseAThisPrimaryNoNewArray(AThisPrimaryNoNewArray node);
    void caseAClassPrimaryNoNewArray(AClassPrimaryNoNewArray node);
    void caseAExpressionPrimaryNoNewArray(AExpressionPrimaryNoNewArray node);
    void caseAIdentifierPrimaryNoNewArray(AIdentifierPrimaryNoNewArray node);
    void caseAFieldPrimaryNoNewArray(AFieldPrimaryNoNewArray node);
    void caseAMethodPrimaryNoNewArray(AMethodPrimaryNoNewArray node);
    void caseAArrayPrimaryNoNewArray(AArrayPrimaryNoNewArray node);
    void caseAPrimitiveArrayCreationExpression(APrimitiveArrayCreationExpression node);
    void caseAReferenceArrayCreationExpression(AReferenceArrayCreationExpression node);
    void caseAPrimitiveInitializerArrayCreationExpression(APrimitiveInitializerArrayCreationExpression node);
    void caseAReferenceInitializerArrayCreationExpression(AReferenceInitializerArrayCreationExpression node);
    void caseAArrayInitializer(AArrayInitializer node);
    void caseAOneVariableInitializers(AOneVariableInitializers node);
    void caseAManyVariableInitializers(AManyVariableInitializers node);
    void caseASimpleEqualityExpression(ASimpleEqualityExpression node);
    void caseAEqEqReEqualityExpression(AEqEqReEqualityExpression node);
    void caseAEqEqIdEqualityExpression(AEqEqIdEqualityExpression node);
    void caseAEqIdReEqualityExpression(AEqIdReEqualityExpression node);
    void caseAEqIdIdEqualityExpression(AEqIdIdEqualityExpression node);
    void caseANeqEqReEqualityExpression(ANeqEqReEqualityExpression node);
    void caseANeqEqIdEqualityExpression(ANeqEqIdEqualityExpression node);
    void caseANeqIdReEqualityExpression(ANeqIdReEqualityExpression node);
    void caseANeqIdIdEqualityExpression(ANeqIdIdEqualityExpression node);
    void caseAEmptyStatement(AEmptyStatement node);
    void caseAExpressionStatement(AExpressionStatement node);
    void caseAOneExAssertStatement(AOneExAssertStatement node);
    void caseAOneIdAssertStatement(AOneIdAssertStatement node);
    void caseATwoExExAssertStatement(ATwoExExAssertStatement node);
    void caseATwoExIdAssertStatement(ATwoExIdAssertStatement node);
    void caseATwoIdExAssertStatement(ATwoIdExAssertStatement node);
    void caseATwoIdIdAssertStatement(ATwoIdIdAssertStatement node);
    void caseAExpressionSwitchStatement(AExpressionSwitchStatement node);
    void caseAIdentifierSwitchStatement(AIdentifierSwitchStatement node);
    void caseAExpressionDoStatement(AExpressionDoStatement node);
    void caseAIdentifierDoStatement(AIdentifierDoStatement node);
    void caseABreakStatement(ABreakStatement node);
    void caseAContinueStatement(AContinueStatement node);
    void caseAEmptyReturnStatement(AEmptyReturnStatement node);
    void caseAExpressionReturnStatement(AExpressionReturnStatement node);
    void caseAIdentifierReturnStatement(AIdentifierReturnStatement node);
    void caseAExpressionSynchronizedStatement(AExpressionSynchronizedStatement node);
    void caseAIdentifierSynchronizedStatement(AIdentifierSynchronizedStatement node);
    void caseAExpressionThrowStatement(AExpressionThrowStatement node);
    void caseAIdentifierThrowStatement(AIdentifierThrowStatement node);
    void caseACatchTryStatement(ACatchTryStatement node);
    void caseAFinallyTryStatement(AFinallyTryStatement node);
    void caseAStatementWithoutTrailingSubstatementStatementNoShortIf(AStatementWithoutTrailingSubstatementStatementNoShortIf node);
    void caseALabeledStatementNoShortIfStatementNoShortIf(ALabeledStatementNoShortIfStatementNoShortIf node);
    void caseAIfThenElseStatementNoShortIfStatementNoShortIf(AIfThenElseStatementNoShortIfStatementNoShortIf node);
    void caseAWhileStatementNoShortIfStatementNoShortIf(AWhileStatementNoShortIfStatementNoShortIf node);
    void caseAForStatementNoShortIfStatementNoShortIf(AForStatementNoShortIfStatementNoShortIf node);
    void caseAEmptyBasicForStatement(AEmptyBasicForStatement node);
    void caseAExpressionBasicForStatement(AExpressionBasicForStatement node);
    void caseAIdentifierBasicForStatement(AIdentifierBasicForStatement node);
    void caseAPrimitiveExpressionEnhancedForStatement(APrimitiveExpressionEnhancedForStatement node);
    void caseAPrimitiveIdentifierEnhancedForStatement(APrimitiveIdentifierEnhancedForStatement node);
    void caseAReferenceExpressionEnhancedForStatement(AReferenceExpressionEnhancedForStatement node);
    void caseAReferenceIdentifierEnhancedForStatement(AReferenceIdentifierEnhancedForStatement node);
    void caseAIntegerLiteral(AIntegerLiteral node);
    void caseAFloatingPointLiteral(AFloatingPointLiteral node);
    void caseABooleanLiteral(ABooleanLiteral node);
    void caseACharacterLiteral(ACharacterLiteral node);
    void caseAStringLiteral(AStringLiteral node);
    void caseANullLiteral(ANullLiteral node);
    void caseASimpleMethodInvocation(ASimpleMethodInvocation node);
    void caseAPrimaryMethodInvocation(APrimaryMethodInvocation node);
    void caseASuperMethodInvocation(ASuperMethodInvocation node);
    void caseAClassNameMethodInvocation(AClassNameMethodInvocation node);
    void caseATypeNameMethodInvocation(ATypeNameMethodInvocation node);
    void caseAExpressionDimExpr(AExpressionDimExpr node);
    void caseAIdentifierDimExpr(AIdentifierDimExpr node);
    void caseADim(ADim node);
    void caseASimpleRelationalExpression(ASimpleRelationalExpression node);
    void caseALtShShRelationalExpression(ALtShShRelationalExpression node);
    void caseALtShIdRelationalExpression(ALtShIdRelationalExpression node);
    void caseALtIdShRelationalExpression(ALtIdShRelationalExpression node);
    void caseALtIdIdRelationalExpression(ALtIdIdRelationalExpression node);
    void caseAGtShShRelationalExpression(AGtShShRelationalExpression node);
    void caseAGtShIdRelationalExpression(AGtShIdRelationalExpression node);
    void caseAGtIdShRelationalExpression(AGtIdShRelationalExpression node);
    void caseAGtIdIdRelationalExpression(AGtIdIdRelationalExpression node);
    void caseALteqShShRelationalExpression(ALteqShShRelationalExpression node);
    void caseALteqShIdRelationalExpression(ALteqShIdRelationalExpression node);
    void caseALteqIdShRelationalExpression(ALteqIdShRelationalExpression node);
    void caseALteqIdIdRelationalExpression(ALteqIdIdRelationalExpression node);
    void caseAGteqShShRelationalExpression(AGteqShShRelationalExpression node);
    void caseAGteqShIdRelationalExpression(AGteqShIdRelationalExpression node);
    void caseAGteqIdShRelationalExpression(AGteqIdShRelationalExpression node);
    void caseAGteqIdIdRelationalExpression(AGteqIdIdRelationalExpression node);
    void caseAInstanceofShPrimitiveRelationalExpression(AInstanceofShPrimitiveRelationalExpression node);
    void caseAInstanceofShReferenceRelationalExpression(AInstanceofShReferenceRelationalExpression node);
    void caseAInstanceofIdPrimitiveRelationalExpression(AInstanceofIdPrimitiveRelationalExpression node);
    void caseAInstanceofIdReferenceRelationalExpression(AInstanceofIdReferenceRelationalExpression node);
    void caseAAssignmentStatementExpression(AAssignmentStatementExpression node);
    void caseAPreIncrementStatementExpression(APreIncrementStatementExpression node);
    void caseAPreDecrementStatementExpression(APreDecrementStatementExpression node);
    void caseAPostIncrementStatementExpression(APostIncrementStatementExpression node);
    void caseAPostDecrementStatementExpression(APostDecrementStatementExpression node);
    void caseAMethodInvocationStatementExpression(AMethodInvocationStatementExpression node);
    void caseASwitchBlock(ASwitchBlock node);
    void caseACatchClause(ACatchClause node);
    void caseAFinally(AFinally node);
    void caseALabeledStatementNoShortIf(ALabeledStatementNoShortIf node);
    void caseAExpressionIfThenElseStatementNoShortIf(AExpressionIfThenElseStatementNoShortIf node);
    void caseAIdentifierIfThenElseStatementNoShortIf(AIdentifierIfThenElseStatementNoShortIf node);
    void caseAExpressionWhileStatementNoShortIf(AExpressionWhileStatementNoShortIf node);
    void caseAIdentifierWhileStatementNoShortIf(AIdentifierWhileStatementNoShortIf node);
    void caseAEmptyForStatementNoShortIf(AEmptyForStatementNoShortIf node);
    void caseAExpressionForStatementNoShortIf(AExpressionForStatementNoShortIf node);
    void caseAIdentifierForStatementNoShortIf(AIdentifierForStatementNoShortIf node);
    void caseAStatementForInit(AStatementForInit node);
    void caseAVariableDeclarationForInit(AVariableDeclarationForInit node);
    void caseAForUpdate(AForUpdate node);
    void caseASimpleShiftExpression(ASimpleShiftExpression node);
    void caseAShlShAdShiftExpression(AShlShAdShiftExpression node);
    void caseAShlShIdShiftExpression(AShlShIdShiftExpression node);
    void caseAShlIdAdShiftExpression(AShlIdAdShiftExpression node);
    void caseAShlIdIdShiftExpression(AShlIdIdShiftExpression node);
    void caseAShrShAdShiftExpression(AShrShAdShiftExpression node);
    void caseAShrShIdShiftExpression(AShrShIdShiftExpression node);
    void caseAShrIdAdShiftExpression(AShrIdAdShiftExpression node);
    void caseAShrIdIdShiftExpression(AShrIdIdShiftExpression node);
    void caseAUshrShAdShiftExpression(AUshrShAdShiftExpression node);
    void caseAUshrShIdShiftExpression(AUshrShIdShiftExpression node);
    void caseAUshrIdAdShiftExpression(AUshrIdAdShiftExpression node);
    void caseAUshrIdIdShiftExpression(AUshrIdIdShiftExpression node);
    void caseAExpressionPreIncrementExpression(AExpressionPreIncrementExpression node);
    void caseAIdentifierPreIncrementExpression(AIdentifierPreIncrementExpression node);
    void caseAExpressionPreDecrementExpression(AExpressionPreDecrementExpression node);
    void caseAIdentifierPreDecrementExpression(AIdentifierPreDecrementExpression node);
    void caseAExpressionPostIncrementExpression(AExpressionPostIncrementExpression node);
    void caseAIdentifierPostIncrementExpression(AIdentifierPostIncrementExpression node);
    void caseAExpressionPostDecrementExpression(AExpressionPostDecrementExpression node);
    void caseAIdentifierPostDecrementExpression(AIdentifierPostDecrementExpression node);
    void caseASwitchBlockStatementGroup(ASwitchBlockStatementGroup node);
    void caseAExpressionSwitchLabel(AExpressionSwitchLabel node);
    void caseAIdentifierSwitchLabel(AIdentifierSwitchLabel node);
    void caseADefaultSwitchLabel(ADefaultSwitchLabel node);
    void caseAOneStatementExpressionList(AOneStatementExpressionList node);
    void caseAManyStatementExpressionList(AManyStatementExpressionList node);
    void caseASimpleAdditiveExpression(ASimpleAdditiveExpression node);
    void caseAPlusAdMuAdditiveExpression(APlusAdMuAdditiveExpression node);
    void caseAPlusAdIdAdditiveExpression(APlusAdIdAdditiveExpression node);
    void caseAPlusIdMuAdditiveExpression(APlusIdMuAdditiveExpression node);
    void caseAPlusIdIdAdditiveExpression(APlusIdIdAdditiveExpression node);
    void caseAMinusAdMuAdditiveExpression(AMinusAdMuAdditiveExpression node);
    void caseAMinusAdIdAdditiveExpression(AMinusAdIdAdditiveExpression node);
    void caseAMinusIdMuAdditiveExpression(AMinusIdMuAdditiveExpression node);
    void caseAMinusIdIdAdditiveExpression(AMinusIdIdAdditiveExpression node);
    void caseAPreIncrementUnaryExpression(APreIncrementUnaryExpression node);
    void caseAPreDecrementUnaryExpression(APreDecrementUnaryExpression node);
    void caseAPlusExpressionUnaryExpression(APlusExpressionUnaryExpression node);
    void caseAPlusIdentifierUnaryExpression(APlusIdentifierUnaryExpression node);
    void caseAMinusExpressionUnaryExpression(AMinusExpressionUnaryExpression node);
    void caseAMinusIdentifierUnaryExpression(AMinusIdentifierUnaryExpression node);
    void caseAUnaryUnaryExpression(AUnaryUnaryExpression node);
    void caseAPrimaryPostfixExpression(APrimaryPostfixExpression node);
    void caseAPostIncrementPostfixExpression(APostIncrementPostfixExpression node);
    void caseAPostDecrementPostfixExpression(APostDecrementPostfixExpression node);
    void caseAConstantExpression(AConstantExpression node);
    void caseASimpleMultiplicativeExpression(ASimpleMultiplicativeExpression node);
    void caseAStarMuUnMultiplicativeExpression(AStarMuUnMultiplicativeExpression node);
    void caseAStarMuIdMultiplicativeExpression(AStarMuIdMultiplicativeExpression node);
    void caseAStarIdUnMultiplicativeExpression(AStarIdUnMultiplicativeExpression node);
    void caseAStarIdIdMultiplicativeExpression(AStarIdIdMultiplicativeExpression node);
    void caseASlashMuUnMultiplicativeExpression(ASlashMuUnMultiplicativeExpression node);
    void caseASlashMuIdMultiplicativeExpression(ASlashMuIdMultiplicativeExpression node);
    void caseASlashIdUnMultiplicativeExpression(ASlashIdUnMultiplicativeExpression node);
    void caseASlashIdIdMultiplicativeExpression(ASlashIdIdMultiplicativeExpression node);
    void caseAPercentMuUnMultiplicativeExpression(APercentMuUnMultiplicativeExpression node);
    void caseAPercentMuIdMultiplicativeExpression(APercentMuIdMultiplicativeExpression node);
    void caseAPercentIdUnMultiplicativeExpression(APercentIdUnMultiplicativeExpression node);
    void caseAPercentIdIdMultiplicativeExpression(APercentIdIdMultiplicativeExpression node);
    void caseAPostfixUnaryExpressionNotPlusMinus(APostfixUnaryExpressionNotPlusMinus node);
    void caseATildeExpressionUnaryExpressionNotPlusMinus(ATildeExpressionUnaryExpressionNotPlusMinus node);
    void caseATildeIdentifierUnaryExpressionNotPlusMinus(ATildeIdentifierUnaryExpressionNotPlusMinus node);
    void caseAEmarkExpressionUnaryExpressionNotPlusMinus(AEmarkExpressionUnaryExpressionNotPlusMinus node);
    void caseAEmarkIdentifierUnaryExpressionNotPlusMinus(AEmarkIdentifierUnaryExpressionNotPlusMinus node);
    void caseACastUnaryExpressionNotPlusMinus(ACastUnaryExpressionNotPlusMinus node);
    void caseAPrimitiveExpressionCastExpression(APrimitiveExpressionCastExpression node);
    void caseAPrimitiveIdentifierCastExpression(APrimitiveIdentifierCastExpression node);
    void caseAReferenceExpressionCastExpression(AReferenceExpressionCastExpression node);
    void caseAReferenceIdentifierCastExpression(AReferenceIdentifierCastExpression node);
    void caseAAbstractModifier(AAbstractModifier node);
    void caseAAnnotationModifier(AAnnotationModifier node);
    void caseAFinalModifier(AFinalModifier node);
    void caseANativeModifier(ANativeModifier node);
    void caseAPrivateModifier(APrivateModifier node);
    void caseAProtectedModifier(AProtectedModifier node);
    void caseAPublicModifier(APublicModifier node);
    void caseAStaticModifier(AStaticModifier node);
    void caseAStrictfpModifier(AStrictfpModifier node);
    void caseASynchronizedModifier(ASynchronizedModifier node);
    void caseATransientModifier(ATransientModifier node);
    void caseAVolatileModifier(AVolatileModifier node);
    void caseAAdditionalIdentifier(AAdditionalIdentifier node);
    void caseATrueBooleanLiteral(ATrueBooleanLiteral node);
    void caseAFalseBooleanLiteral(AFalseBooleanLiteral node);

    void caseTAt(TAt node);
    void caseTComma(TComma node);
    void caseTDot(TDot node);
    void caseTDotDotDot(TDotDotDot node);
    void caseTLBkt(TLBkt node);
    void caseTLBrc(TLBrc node);
    void caseTLPar(TLPar node);
    void caseTRBkt(TRBkt node);
    void caseTRBrc(TRBrc node);
    void caseTRPar(TRPar node);
    void caseTSemi(TSemi node);
    void caseTAmp(TAmp node);
    void caseTAmpAmp(TAmpAmp node);
    void caseTAmpAssign(TAmpAssign node);
    void caseTAssign(TAssign node);
    void caseTBar(TBar node);
    void caseTBarAssign(TBarAssign node);
    void caseTBarBar(TBarBar node);
    void caseTCaret(TCaret node);
    void caseTCaretAssign(TCaretAssign node);
    void caseTColon(TColon node);
    void caseTEmark(TEmark node);
    void caseTEq(TEq node);
    void caseTGt(TGt node);
    void caseTGteq(TGteq node);
    void caseTLt(TLt node);
    void caseTLteq(TLteq node);
    void caseTMinus(TMinus node);
    void caseTMinusAssign(TMinusAssign node);
    void caseTMinusMinus(TMinusMinus node);
    void caseTNeq(TNeq node);
    void caseTPercent(TPercent node);
    void caseTPercentAssign(TPercentAssign node);
    void caseTPlus(TPlus node);
    void caseTPlusAssign(TPlusAssign node);
    void caseTPlusPlus(TPlusPlus node);
    void caseTQmark(TQmark node);
    void caseTShl(TShl node);
    void caseTShlAssign(TShlAssign node);
    void caseTShr(TShr node);
    void caseTShrAssign(TShrAssign node);
    void caseTSlash(TSlash node);
    void caseTSlashAssign(TSlashAssign node);
    void caseTStar(TStar node);
    void caseTStarAssign(TStarAssign node);
    void caseTTilde(TTilde node);
    void caseTUshr(TUshr node);
    void caseTUshrAssign(TUshrAssign node);
    void caseTAbstract(TAbstract node);
    void caseTAssert(TAssert node);
    void caseTBoolean(TBoolean node);
    void caseTBreak(TBreak node);
    void caseTByte(TByte node);
    void caseTCase(TCase node);
    void caseTCatch(TCatch node);
    void caseTChar(TChar node);
    void caseTClassToken(TClassToken node);
    void caseTConst(TConst node);
    void caseTContinue(TContinue node);
    void caseTDefault(TDefault node);
    void caseTDo(TDo node);
    void caseTDouble(TDouble node);
    void caseTElse(TElse node);
    void caseTEnum(TEnum node);
    void caseTExtends(TExtends node);
    void caseTFalse(TFalse node);
    void caseTFinal(TFinal node);
    void caseTFinallyToken(TFinallyToken node);
    void caseTFloat(TFloat node);
    void caseTFor(TFor node);
    void caseTGoto(TGoto node);
    void caseTIf(TIf node);
    void caseTImplements(TImplements node);
    void caseTInstanceof(TInstanceof node);
    void caseTInt(TInt node);
    void caseTInterface(TInterface node);
    void caseTLong(TLong node);
    void caseTNative(TNative node);
    void caseTNew(TNew node);
    void caseTPackage(TPackage node);
    void caseTPrivate(TPrivate node);
    void caseTProtected(TProtected node);
    void caseTPublic(TPublic node);
    void caseTReturn(TReturn node);
    void caseTShort(TShort node);
    void caseTStatic(TStatic node);
    void caseTStrictfp(TStrictfp node);
    void caseTSuperToken(TSuperToken node);
    void caseTSwitch(TSwitch node);
    void caseTSynchronized(TSynchronized node);
    void caseTThis(TThis node);
    void caseTThrow(TThrow node);
    void caseTThrowsToken(TThrowsToken node);
    void caseTTransient(TTransient node);
    void caseTTrue(TTrue node);
    void caseTTry(TTry node);
    void caseTVoid(TVoid node);
    void caseTVolatile(TVolatile node);
    void caseTWhile(TWhile node);
    void caseTNamespace(TNamespace node);
    void caseTViewpoint(TViewpoint node);
    void caseTVirtualmodel(TVirtualmodel node);
    void caseTUse(TUse node);
    void caseTAs(TAs node);
    void caseTImport(TImport node);
    void caseTModelslot(TModelslot node);
    void caseTFlexoconcept(TFlexoconcept node);
    void caseTRole(TRole node);
    void caseTCharacterLiteral(TCharacterLiteral node);
    void caseTFloatingPointLiteral(TFloatingPointLiteral node);
    void caseTIntegerLiteral(TIntegerLiteral node);
    void caseTNullLiteral(TNullLiteral node);
    void caseTStringLiteral(TStringLiteral node);
    void caseTIdentifier(TIdentifier node);
    void caseTWhiteSpace(TWhiteSpace node);
    void caseTTraditionalComment(TTraditionalComment node);
    void caseTDocumentationComment(TDocumentationComment node);
    void caseTEndOfLineComment(TEndOfLineComment node);
    void caseTUrl(TUrl node);
    void caseTTaIdentifier(TTaIdentifier node);
    void caseEOF(EOF node);
}
